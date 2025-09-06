import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastService } from './toast.service';

@Component({
    standalone: true,
    selector: 'app-toast',
    imports: [CommonModule],
    template: `
    <div class="toast" *ngIf="toast.message() as msg">{{ msg }}</div>
  `,
    styles: [`
    .toast {
      position: fixed;
      right: 16px;
      bottom: 16px;
      background: #111827;
      color: #e5e7eb;
      border: 1px solid #374151;
      padding: 10px 14px;
      border-radius: 10px;
      box-shadow: 0 2px 8px rgba(0,0,0,.3);
      z-index: 9999;
      animation: fadein 0.3s, fadeout 0.3s 3.7s;
    }
    @keyframes fadein { from {opacity:0;} to {opacity:1;} }
    @keyframes fadeout { from {opacity:1;} to {opacity:0;} }
  `]
})
export class ToastComponent {
    toast = inject(ToastService);
}
