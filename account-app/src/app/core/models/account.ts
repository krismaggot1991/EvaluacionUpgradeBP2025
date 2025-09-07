export interface Account {
    id?: number;
    number: string;
    accountType: 'AHORROS' | 'CORRIENTE';
    initialBalance: number;
    status: boolean;
    clientIdentification: string;
}