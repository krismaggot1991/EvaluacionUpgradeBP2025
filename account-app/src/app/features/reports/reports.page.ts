import { Component, inject, signal } from '@angular/core';
import { ReportsApi } from '../../core/services/reports.api';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { StatusReport } from '../../core/models/status-report';
import jsPDF from 'jspdf';

@Component({
    standalone: true,
    selector: 'app-reports',
    imports: [CommonModule, ReactiveFormsModule],
    templateUrl: './reports.page.html',
    styleUrls: ['./reports.page.css']
})
export class ReportsPage {
    private api = inject(ReportsApi);
    private fb = inject(FormBuilder);

    form = this.fb.group({
        initialDate: ['', Validators.required],
        endDate: ['', Validators.required],
        clientIdentification: ['', Validators.required],
    });

    data = signal<StatusReport | undefined>(undefined);
    loading = signal(false);

    fetch() {
        if (this.form.invalid) return this.form.markAllAsTouched();
        const { initialDate, endDate, clientIdentification } = this.form.getRawValue()!;
        this.loading.set(true);
        this.api.getByRange(initialDate!, endDate!, clientIdentification!)
            .subscribe({
                next: r => this.data.set(r),
                error: e => alert(e?.error?.message || 'Error'),
                complete: () => this.loading.set(false)
            });
    }

    downloadPdf() {
        const report = this.data();
        if (!report) return;

        // Si el backend devuelve PDF en base64
        if (report.pdfBase64) {
            const bytes = atob(report.pdfBase64);
            const arr = new Uint8Array(bytes.length);
            for (let i = 0; i < bytes.length; i++) arr[i] = bytes.charCodeAt(i);
            const blob = new Blob([arr], { type: 'application/pdf' });
            const url = URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url; a.download = 'estado-de-cuenta.pdf'; a.click();
            URL.revokeObjectURL(url);
            return;
        }

        // Si no hay PDF desde el backend → generamos en front con jsPDF
        const doc = new jsPDF();
        doc.setFontSize(14);
        doc.text(`Estado de cuenta - Cliente: ${report.name}`, 10, 15);

        let y = 25;
        report.accounts.forEach(acc => {
            doc.setFontSize(12);
            doc.text(`Cuenta ${acc.number} (${acc.accountType})`, 10, y);
            // y += 6;
            //doc.text(`Saldo inicial: ${acc.initialBalance}`, 10, y);
            //y += 6;
            // doc.text(`Créditos: ${acc.totalCredits}  Débitos: ${acc.totalDebits}`, 10, y);
            y += 6;
            doc.text(`Saldo disponible: ${acc.initialBalance}`, 10, y);
            y += 8;

            doc.setFontSize(10);
            doc.text('Movimientos:', 10, y);
            y += 6;
            acc.movements.forEach(m => {
                doc.text(`${m.date}  mov: ${m.value}  saldo: ${m.balance}`, 12, y);
                y += 5;
                if (y > 270) { doc.addPage(); y = 20; }
            });
            y += 10;
        });

        doc.save('estado-de-cuenta.pdf');
    }
}
