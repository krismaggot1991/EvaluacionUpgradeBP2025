import { Injectable, inject } from '@angular/core';
import { HttpService } from './http.service';
import { Client } from '../models/client';
@Injectable({ providedIn: 'root' })
export class ClientsApi {
    private http = inject(HttpService);
    list() { return this.http.get<Client[]>('/client'); }
    get(id: number) { return this.http.get<Client>(`/client/${id}`); }
    create(dto: Client) { return this.http.post<Client>('/client', dto); }
    update(id: number, dto: Client) { return this.http.put<Client>(`/client/${id}`, dto); }
    remove(id: number) { return this.http.delete<void>(`/client/${id}`); }
}
