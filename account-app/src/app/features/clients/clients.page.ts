import { Component, computed, effect, inject, signal } from '@angular/core';
import { ClientsApi } from '../../core/services/clients.api';
import { Client } from '../../core/models/client';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { debounceTime } from 'rxjs/operators';

@Component({
    standalone: true,
    selector: 'app-clients',
    imports: [CommonModule, ReactiveFormsModule],
    templateUrl: './clients.page.html',
    styleUrls: ['./clients.page.css']
})
export class ClientsPage {
    private api = inject(ClientsApi);
    private fb = inject(FormBuilder);

    items = signal<Client[]>([]);
    loading = signal(false);
    q = this.fb.control('', { nonNullable: true });
    form = this.fb.group({
        id: [undefined as number | undefined],
        name: ['', [Validators.required, Validators.minLength(3)]],
        gender: ['O', Validators.required],
        age: [18, [Validators.required, Validators.min(0)]],
        identification: ['', Validators.required],
        address: ['', Validators.required],
        phone: ['', Validators.required],
        status: [true, Validators.required],
    });

    filtered = computed(() => {
        const term = (this.q.value ?? '').toLowerCase().trim();
        if (!term) return this.items();
        return this.items().filter(c =>
            [c.name, c.identification, c.address, c.phone].some(v => (v ?? '').toLowerCase().includes(term))
        );
    });

    constructor() {
        this.load();
        this.q.valueChanges.pipe(debounceTime(250)).subscribe(); // solo para refrescar computed
    }

    load() {
        this.loading.set(true);
        this.api.list().subscribe({
            next: res => this.items.set(res),
            error: err => alert(err?.userMessage || err?.message || 'Error inesperado'),
            complete: () => this.loading.set(false)
        });
    }

    edit(row: Client) { this.form.patchValue(row); }
    clear() { this.form.reset({ status: true, gender: 'O', age: 18 }); }

    save() {
        if (this.form.invalid) return this.form.markAllAsTouched();
        const dto = this.form.getRawValue() as Client;
        const req$ = dto.id ? this.api.update(dto.id!, dto) : this.api.create(dto);
        req$.subscribe({
            next: _ => { this.clear(); this.load(); },
            error: err => alert(this.msg(err))
        });
    }

    remove(id?: number) {
        if (!id || !confirm('¿Eliminar cliente?')) return;
        this.api.remove(id).subscribe({ next: _ => this.load(), error: err => alert(this.msg(err)) });
    }

    private msg(err: any) {
        return err?.error?.message || err?.message || 'Error inesperado';
    }
}
