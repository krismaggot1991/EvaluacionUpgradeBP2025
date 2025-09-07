export interface Client {
    id?: number;
    name: string;
    gender: 'MASCULINO' | 'FEMENINO' | 'OTRO';
    age: number;
    identification: string;
    address: string;
    phone: string;
    status: boolean;
    password: string;
}
