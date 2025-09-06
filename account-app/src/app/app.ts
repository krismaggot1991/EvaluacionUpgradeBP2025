import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ToastComponent } from './shared/toast/toast.component';

@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterOutlet, ToastComponent],
  template: `
    <header class="topbar">
      <h1>Bank App</h1>
      <nav class="nav">
        <a routerLink="/clientes" routerLinkActive="active">Clientes</a>
        <a routerLink="/cuentas" routerLinkActive="active">Cuentas</a>
        <a routerLink="/movimientos" routerLinkActive="active">Movimientos</a>
        <a routerLink="/reportes" routerLinkActive="active">Reportes</a>
      </nav>
    </header>
    <main class="container">
      <router-outlet></router-outlet>
    </main>
    <app-toast></app-toast>
  `,
  styles: [`
    :host { color: #e5e7eb; font-family: system-ui, -apple-system, Segoe UI, Roboto, Arial; }
    .topbar { display:flex; align-items:center; justify-content:space-between; padding:10px 16px; background:#0f172a; }
    .nav { display:flex; gap:12px; }
    a { color:#cbd5e1; text-decoration:none; padding:6px 10px; border-radius:8px; }
    a.active, a:hover { background:#111827; color:#fff; }
    .container { padding: 16px; background:#0b1220; min-height: calc(100dvh - 52px); }
  `]
})
export class App {
  protected readonly title = signal('account-app');
}
