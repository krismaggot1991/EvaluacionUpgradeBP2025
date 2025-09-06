import { Component, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { debounceTime } from 'rxjs/operators';

import { Movement, MovementType } from '../../core/models/movement';
import { MovementsApi } from '../../core/services/movements.api';

@Component({
    standalone: true,
    selector: 'app-movements',
    imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
    templateUrl: './movements.page.html',
    styleUrls: ['./movements.page.css']
})
export class MovementsPage {
    private api = inject(MovementsApi);
    private fb = inject(FormBuilder);

    loading = signal(false);
    items = signal<Movement[]>([]);

    // Búsqueda rápida
    q = this.fb.control('', { nonNullable: true });

    // Formulario CRUD
    form = this.fb.group({
        id: [undefined as number | undefined],
        // Fecha es opcional; el backend puede setearla
        date: [''],
        movementType: ['CREDIT' as MovementType, Validators.required],
        value: [0, [Validators.required, Validators.min(0.01)]],
        balance: [{ value: undefined as number | undefined, disabled: true }],
        accountId: [undefined as number | undefined, [Validators.required, Validators.min(1)]],
        accountNumber: [''] // opcional / solo display si lo devuelve el back
    });

    // Lista filtrada por búsqueda rápida
    filtered = computed(() => {
        const term = (this.q.value ?? '').trim().toLowerCase();
        if (!term) return this.items();
        return this.items().filter(m =>
            [
                m.accountNumber ?? '',
                m.movementType ?? '',
                String(m.value ?? ''),
                String(m.balance ?? ''),
                m.date ?? ''
            ]
                .filter(Boolean)
                .some(v => (v as string).toLowerCase().includes(term))
        );
    });

    constructor() {
        this.load();
        this.q.valueChanges.pipe(debounceTime(250)).subscribe(); // trigger recompute
    }

    load() {
        this.loading.set(true);
        this.api.list().subscribe({
            next: res => this.items.set(res),
            error: err => alert(this.msg(err)),
            complete: () => this.loading.set(false)
        });
    }

    edit(row: Movement) {
        // balance se muestra solo lectura
        this.form.reset({
            id: row.id,
            date: row.date ?? '',
            movementType: row.movementType,
            value: Math.abs(row.value ?? 0),
            balance: row.balance,
            accountId: row.accountId,
            accountNumber: row.accountNumber ?? ''
        });
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    clear() {
        this.form.reset({
            movementType: 'CREDIT',
            value: 0,
            balance: undefined,
            accountId: undefined,
            accountNumber: ''
        });
    }

    save() {
        if (this.form.invalid) {
            this.form.markAllAsTouched();
            return;
        }
        // Clonar valores (balance está disabled, por eso getRawValue)
        const raw = this.form.getRawValue() as Movement;

        // Normalizar signo según tipo
        const normalized: Movement = {
            ...raw,
            value:
                raw.movementType === 'DEBIT'
                    ? -Math.abs(raw.value ?? 0)
                    : Math.abs(raw.value ?? 0)
        };

        // No enviamos balance ni accountNumber en create/update (los maneja back)
        delete (normalized as any).balance;
        delete (normalized as any).accountNumber;

        const req$ = normalized.id
            ? this.api.update(normalized.id!, normalized)
            : this.api.create(normalized);

        req$.subscribe({
            next: saved => {
                // Mostrar balance actualizado si el back lo devuelve
                this.clear();
                this.load();
            },
            error: err => alert(this.msg(err))
        });
    }

    remove(id?: number) {
        if (!id) return;
        if (!confirm('¿Eliminar movimiento?')) return;
        this.api.remove(id).subscribe({
            next: _ => this.load(),
            error: err => alert(this.msg(err))
        });
    }

    private msg(err: any) {
        return err?.error?.message || err?.message || 'Error inesperado';
    }
}
