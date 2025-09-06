import { Component, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Account } from '../../core/models/account';
import { AccountsApi } from '../../core/services/accounts.api';
import { debounceTime } from 'rxjs/operators';
import { HttpClientModule } from '@angular/common/http';

@Component({
    standalone: true,
    selector: 'app-accounts',
    imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
    templateUrl: './accounts.page.html',
    styleUrls: ['./accounts.page.css']
})
export class AccountsPage {
    private api = inject(AccountsApi);
    private fb = inject(FormBuilder);

    loading = signal(false);
    items = signal<Account[]>([]);

    // Búsqueda rápida
    q = this.fb.control('', { nonNullable: true });

    // Formulario CRUD
    form = this.fb.group({
        id: [undefined as number | undefined],
        number: ['', [Validators.required, Validators.minLength(4)]],
        type: ['Ahorro' as 'Ahorro' | 'Corriente' | 'Ahorros', Validators.required],
        initialBalance: [0, [Validators.required, Validators.min(0)]],
        status: [true, Validators.required],
        clientIdentification: ['', Validators.required],
    });

    // Lista filtrada por búsqueda rápida
    filtered = computed(() => {
        const term = (this.q.value ?? '').trim().toLowerCase();
        if (!term) return this.items();
        return this.items().filter(a =>
            [
                a.number,
                a.type,
                String(a.initialBalance),
                a.clientIdentification
            ]
                .filter(Boolean)
                .some(v => (v as string).toLowerCase().includes(term))
        );
    });

    constructor() {
        this.load();
        this.q.valueChanges.pipe(debounceTime(250)).subscribe(); // dispara recomputación
    }

    load() {
        this.loading.set(true);
        this.api.list().subscribe({
            next: res => this.items.set(res),
            error: err => alert(err?.userMessage || err?.message || 'Error inesperado'),
            complete: () => this.loading.set(false)
        });
    }

    edit(row: Account) {
        this.form.patchValue(row);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    clear() {
        this.form.reset({
            type: 'Ahorro',
            initialBalance: 0,
            status: true
        });
    }

    save() {
        if (this.form.invalid) {
            this.form.markAllAsTouched();
            return;
        }
        const dto = this.form.getRawValue() as Account;
        const req$ = dto.id ? this.api.update(dto.id!, dto) : this.api.create(dto);
        req$.subscribe({
            next: _ => { this.clear(); this.load(); },
            error: err => alert(this.msg(err))
        });
    }

    remove(id?: number) {
        if (!id) return;
        if (!confirm('¿Eliminar cuenta?')) return;
        this.api.remove(id).subscribe({
            next: _ => this.load(),
            error: err => alert(this.msg(err))
        });
    }

    private msg(err: any) {
        return err?.error?.message || err?.message || 'Error inesperado';
    }
}
