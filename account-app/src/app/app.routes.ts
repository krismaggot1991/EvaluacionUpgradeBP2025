import { Routes } from '@angular/router';
import { ClientsPage } from './features/clients/clients.page';
import { AccountsPage } from './features/accounts/accounts.page';
import { MovementsPage } from './features/movements/movements.page';
import { ReportsPage } from './features/reports/reports.page';

export const routes: Routes = [
    { path: '', redirectTo: 'clientes', pathMatch: 'full' },
    { path: 'clientes', loadComponent: () => import('./features/clients/clients.page').then(m => m.ClientsPage) },
    { path: 'cuentas', loadComponent: () => import('./features/accounts/accounts.page').then(m => m.AccountsPage) },
    { path: 'movimientos', loadComponent: () => import('./features/movements/movements.page').then(m => m.MovementsPage) },
    { path: 'reportes', loadComponent: () => import('./features/reports/reports.page').then(m => m.ReportsPage) },
    { path: '**', redirectTo: 'clientes' }
];