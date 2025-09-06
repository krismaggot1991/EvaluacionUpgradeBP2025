import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';
import { Movement } from '../models/movement';

@Injectable({ providedIn: 'root' })
export class MovementsApi {
    private http = inject(HttpService);

    /** Lista todos los movimientos */
    list(params?: Record<string, any>): Observable<Movement[]> {
        return this.http.get<Movement[]>('/movement', params);
    }

    /** Obtiene un movimiento por id */
    get(id: number): Observable<Movement> {
        return this.http.get<Movement>(`/movement/${id}`);
    }

    /** Crea un movimiento */
    create(dto: Movement): Observable<Movement> {
        return this.http.post<Movement>('/movement', dto);
    }

    /** Actualiza un movimiento existente */
    update(id: number, dto: Movement): Observable<Movement> {
        return this.http.put<Movement>(`/movement/${id}`, dto);
    }

    /** Elimina un movimiento por id */
    remove(id: number): Observable<void> {
        return this.http.delete<void>(`/movement/${id}`);
    }
}
