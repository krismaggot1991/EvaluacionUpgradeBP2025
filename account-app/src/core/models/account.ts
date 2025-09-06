export interface Account {
    id?: number;
    number: string;
    type: 'Ahorro' | 'Corriente' | 'Ahorros';
    initialBalance: number;
    status: boolean;
    clientIdentification: string;
}