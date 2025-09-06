export interface Client {
    id?: number;
    name: string;
    gender: 'M' | 'F' | 'O';
    age: number;
    identification: string;
    address: string;
    phone: string;
    status: boolean;
    // password existe en back pero NO lo mostraremos ni editaremos aquí
}