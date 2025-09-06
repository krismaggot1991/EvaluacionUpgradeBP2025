import { Injectable, inject } from '@angular/core';
import { HttpService } from './http.service';
import { StatusReport } from '../models/status-report';

@Injectable({ providedIn: 'root' })
export class ReportsApi {
    private http = inject(HttpService);
    getByRange(initialDate: string, endDate: string, clientIdentification: string) {
        return this.http.get<StatusReport>('/account/report', { initialDate, endDate, clientIdentification });
    }
}
