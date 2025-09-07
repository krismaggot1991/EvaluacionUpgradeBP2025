import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ToastComponent } from './shared/toast/toast.component';

@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterOutlet, ToastComponent, RouterLink, RouterLinkActive],
  template: `
    <header class="topbar navigation-header" role="banner">
      <div class="brand" aria-label="Aplicación bancaria">
        <span class="brand__mark" aria-hidden="true"></span>
        <img src="assets/images/logoBP.png" alt="Banco Pichincha" class="brand__logo" />
      </div>

      <nav class="nav" role="navigation" aria-label="Navegación principal">
        <a routerLink="/clientes" routerLinkActive="active" [routerLinkActiveOptions]="{ exact: true }">Clientes</a>
        <a routerLink="/cuentas" routerLinkActive="active">Cuentas</a>
        <a routerLink="/movimientos" routerLinkActive="active">Movimientos</a>
        <a routerLink="/reportes" routerLinkActive="active">Reportes</a>
      </nav>
    </header>

    <!-- Contenedor de página global (usa .page-container de tus globales) -->
    <main class="page-container" role="main">
      <router-outlet></router-outlet>
    </main>

    <!-- Toast global -->
    <app-toast></app-toast>
  `,
  styles: [`
    /* ===== Usa variables con fallback para look Banco Pichincha ===== */
   :host{
    --bp-yellow: var(--bp-yellow, #FFDD00);
    --bp-navy:   var(--bp-navy,   #0F265C);
  }

  .topbar{
    display:flex; align-items:center; justify-content:space-between;
    gap: 12px; padding: 10px 16px;
    background: var(--bp-navy);   /* <-- asegura fondo oscuro */
    color:#fff;
    box-shadow: 0 1px 2px rgba(0,0,0,.12);
  }

  .nav{
    display:flex; gap: 8px; align-items:center; flex-wrap: wrap;
  }

  /* Forzar color de links del menú (aunque haya estilos globales) */
  .nav a,
  .nav a:visited {
    color:#000000 !important;        /* tamaño y peso mejorados */
    text-decoration:none;
    padding: 8px 12px; border-radius: 10px;
    position: relative;
    font-size: 1.05rem;
    font-weight: 600;
    letter-spacing: .2px;
    transition: background .18s ease, color .18s ease, transform .12s ease, box-shadow .18s ease;
  }
  .nav a:hover,
  .nav a:visited:hover,
  .nav a.active:hover{
    background: var(--bp-yellow);
    color: #0f265c !important;
    text-shadow: 0 1px 0 rgba(0,0,0,.15);
    transform: translateY(-1px);
    box-shadow: 0 4px 10px rgba(0,0,0,.18);
  }
  .nav a.active{
    background: rgba(255,255,255,.12);
  }
  .nav a.active::after{
    content:"";
    position:absolute; left:10px; right:10px; bottom: -6px;
    height: 3px; border-radius: 3px;
    background: var(--bp-yellow);
  }
  .brand__logo {
    height: 28px;         /* o el tamaño que quieras */
    object-fit: contain;
  }
  `]
})
export class App {
  protected readonly title = signal('account-app');
}
