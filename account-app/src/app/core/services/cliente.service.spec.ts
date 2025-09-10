import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';

import { ClientsApi } from './clients.api';          // ⬅️ Ajusta si tu archivo se llama distinto
import { HttpService } from './http.service';
import { Client } from '../models/client';

describe('ClientsApi', () => {
    let api: ClientsApi;
    let httpMock: jest.Mocked<HttpService>;

    beforeEach(() => {
        httpMock = {
            get: jest.fn(),
            post: jest.fn(),
            put: jest.fn(),
            delete: jest.fn()
        } as unknown as jest.Mocked<HttpService>;

        TestBed.configureTestingModule({
            providers: [
                ClientsApi,
                { provide: HttpService, useValue: httpMock }
            ]
        });

        api = TestBed.inject(ClientsApi);
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('list() debe llamar GET /client y devolver la lista', (done) => {
        const mock: Client[] = [
            { id: 1, nombre: 'Jose Lema' } as unknown as Client
        ];

        httpMock.get.mockReturnValue(of(mock));

        api.list().subscribe((res) => {
            expect(httpMock.get).toHaveBeenCalledWith('/client');
            expect(res).toEqual(mock);
            done();
        });
    });

    it('get(id) debe llamar GET /client/:id y devolver el cliente', (done) => {
        const id = '123';
        const mock: Client = { id: 123, nombre: 'Marianela' } as unknown as Client;

        httpMock.get.mockReturnValue(of(mock));

        api.get(id).subscribe((res) => {
            expect(httpMock.get).toHaveBeenCalledWith(`/client/${id}`);
            expect(res).toEqual(mock);
            done();
        });
    });

    it('create(dto) debe llamar POST /client con el payload y devolver el creado', (done) => {
        const payload = {
            nombre: 'Juan',
            identificacion: '010101',
            estado: true
        } as unknown as Client;

        const created = { id: 99, ...payload } as unknown as Client;

        httpMock.post.mockReturnValue(of(created));

        api.create(payload).subscribe((res) => {
            expect(httpMock.post).toHaveBeenCalledWith('/client', payload);
            expect(res).toEqual(created);
            done();
        });
    });

    it('update(id, dto) debe llamar PUT /client/:id con el payload y devolver el actualizado', (done) => {
        const id = 10;
        const payload = {
            nombre: 'Actualizado',
            identificacion: '020202',
            estado: false
        } as unknown as Client;

        const updated = { id, ...payload } as unknown as Client;

        httpMock.put.mockReturnValue(of(updated));

        api.update(id, payload).subscribe((res) => {
            expect(httpMock.put).toHaveBeenCalledWith(`/client/${id}`, payload);
            expect(res).toEqual(updated);
            done();
        });
    });

    it('remove(id) debe llamar DELETE /client/:id y completar', (done) => {
        const id = 77;
        httpMock.delete.mockReturnValue(of(void 0));

        api.remove(id).subscribe(() => {
            expect(httpMock.delete).toHaveBeenCalledWith(`/client/${id}`);
            done();
        });
    });
});
