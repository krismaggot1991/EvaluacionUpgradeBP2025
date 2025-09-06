import { Routes } from '@angular/router';
import { ClientsPage } from './features/clients/clients.page';
import { AccountsPage } from './features/accounts/accounts.page';
//import { MovementsPage } from './features/movements/movements.page';
import { ReportsPage } from './features/reports/reports.page';

export const routes: Routes = [
    { path: '', redirectTo: 'clientes', pathMatch: 'full' },
    { path: 'clientes', component: ClientsPage, title: 'Clientes' },
    { path: 'cuentas', component: AccountsPage, title: 'Cuentas' },
    //  { path: 'movimientos', component: MovementsPage, title: 'Movimientos' },
    { path: 'reportes', component: ReportsPage, title: 'Reportes' },
];
