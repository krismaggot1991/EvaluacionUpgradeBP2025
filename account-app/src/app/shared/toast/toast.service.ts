import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ToastService {
    message = signal<string | null>(null);

    show(msg: string, timeoutMs = 4000) {
        this.message.set(msg);
        setTimeout(() => this.message.set(null), timeoutMs);
    }
}
