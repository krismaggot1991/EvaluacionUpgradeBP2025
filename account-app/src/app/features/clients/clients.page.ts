import { Component, computed, effect, inject, signal } from '@angular/core';
import { ClientsApi } from '../../core/services/clients.api';
import { Client } from '../../core/models/client';
import {FormBuilder, FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import { CommonModule } from '@angular/common';
import {debounceTime, distinctUntilChanged, map, shareReplay, startWith} from 'rxjs/operators';
import {combineLatest, Observable} from 'rxjs';

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

  /** Caja de búsqueda reactiva */
    search = new FormControl<string>('', { nonNullable: true });

  /** Fuente de datos (cacheada) */
    clients$: Observable<Client[]> = this.api.list().pipe(shareReplay(1));

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
        password: ['', [Validators.required]],
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
        this.form.controls.id.valueChanges.subscribe(id => {
            const ctrl = this.form.controls.password;
            if (!id) {
                ctrl.setValidators([Validators.required, Validators.minLength(6)]);
            } else {
                ctrl.clearValidators();
                ctrl.setValue(''); // no forzamos cambio en edición
            }
            ctrl.updateValueAndValidity({ emitEvent: false });
        });
    }

    load() {
        this.loading.set(true);
        this.api.list().subscribe({
            next: res => this.items.set(res),
            error: err => alert(err?.userMessage || err?.message || 'Error inesperado'),
            complete: () => this.loading.set(false)
        });
    }

    edit(row: Client) {
        if (!row.identification) return;
        // Opcional: spinner local mientras trae el detalle
        this.loading.set(true);
        this.api.get(row.identification.toString()).subscribe({
            next: (full) => {
                // full debe venir con `password` desde el back
                this.form.reset(full); // parchea todo tal cual
                window.scrollTo({ top: 0, behavior: 'smooth' });
            },
            error: (err) => alert(this.msg(err)),
            complete: () => this.loading.set(false)
        });
    }

    clear() {
        this.form.reset({ status: true, gender: 'MASCULINO', age: 18, password: '' });
    }

    save() {
        if (this.form.invalid) { this.form.markAllAsTouched(); return; }
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
  /** Lista filtrada según se escribe */
  filtered$: Observable<Client[]> = combineLatest([
    this.clients$,
    this.search.valueChanges.pipe(startWith(''), debounceTime(150), distinctUntilChanged())
  ]).pipe(
    map(([clients, term]) => filterClients(clients, term))
  );

  /** trackBy para rendimiento en *ngFor */
  trackById = (_: number, c: Client) => (c as any).id ?? (c as any).identificacion ?? _; // fallback
}


/** Normaliza cadenas: minúsculas y sin tildes */
function normalize(s: unknown): string {
  const str = (s ?? '').toString().toLowerCase();
  // quita diacríticos
  return str.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
}


/** Convierte un cliente a texto buscable (ajusta campos si lo necesitas) */
function clientToSearchText(c: Client): string {
  // Incluye los campos típicos del PDF: nombre, identificacion, direccion, telefono, genero, edad, estado
  const anyC = c as any;
  const parts = [
    anyC.nombre,
    anyC.identificacion,
    anyC.direccion,
    anyC.telefono,
    anyC.genero,
    anyC.edad,
    anyC.estado
  ];
  return normalize(parts.filter(Boolean).join(' '));
}

/** Aplica el filtro al arreglo de clientes */
function filterClients(clients: Client[], term: string): Client[] {
  const q = normalize(term);
  if (!q) return clients;
  return clients.filter(c => clientToSearchText(c).includes(q));
}
