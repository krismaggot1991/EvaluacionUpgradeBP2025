import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';
import { Account } from '../models/account';

@Injectable({ providedIn: 'root' })
export class AccountsApi {
    private http = inject(HttpService);

    /** Lista todas las cuentas */
    list(params?: Record<string, any>): Observable<Account[]> {
        return this.http.get<Account[]>('/account', params);
    }

    /** Obtiene una cuenta por id */
    get(id: number): Observable<Account> {
        return this.http.get<Account>(`/account/${id}`);
    }

    /** Crea una cuenta */
    create(dto: Account): Observable<Account> {
        return this.http.post<Account>('/account', dto);
    }

    /** Actualiza una cuenta existente */
    update(id: number, dto: Account): Observable<Account> {
        return this.http.put<Account>(`/account/${id}`, dto);
    }

    /** Elimina una cuenta por id */
    remove(id: number): Observable<void> {
        return this.http.delete<void>(`/account/${id}`);
    }
}
