export interface StatusReport {
    name: string;
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
            value: number;
            balance: number;
        }>
    }>;
    pdfBase64?: string; // si el back lo provee
}