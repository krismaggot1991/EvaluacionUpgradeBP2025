export interface StatusReport {
    client: string;
    accounts: Array<{
        number: string;
        accountType: string;
        initialBalance: number;
        status: boolean;
        totalCredits: number;
        totalDebits: number;
        availableBalance: number;
        movements: Array<{
            date: string;
            movement: number;
            balance: number;
        }>
    }>;
    pdfBase64?: string; // si el back lo provee
}