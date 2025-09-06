export type MovementType = 'CREDIT' | 'DEBIT';
export interface Movement {
    id?: number;
    date?: string;
    movementType: MovementType;
    value: number;   // positivo crédito / negativo débito lo hace el back
    balance?: number;
    accountId: number;
    accountNumber?: string;
}