import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { inject } from '@angular/core';
import { ToastService } from '../../shared/toast/toast.service';

/**
 * Interceptor de errores HTTP
 * - Centraliza el manejo de errores
 * - Muestra el mensaje con ToastService
 */
export const errorInterceptor: HttpInterceptorFn = (req, next) => {
    const toast = inject(ToastService);

    return next(req).pipe(
        catchError((err: HttpErrorResponse) => {
            let userMessage = 'Error inesperado';

            if (err?.error?.message && typeof err.error.message === 'string') {
                userMessage = err.error.message;
            } else if (err.status === 0) {
                userMessage = 'No se pudo conectar con el servidor';
            } else if (err.status === 400) {
                userMessage = 'Solicitud inválida';
            } else if (err.status === 401) {
                userMessage = 'No autorizado';
            } else if (err.status === 404) {
                userMessage = 'Recurso no encontrado';
            } else if (err.status >= 500) {
                userMessage = 'Error interno del servidor';
            }

            // Mostrar toast
            toast.show(userMessage);

            // Log técnico en consola
            console.error(`HTTP ${err.status}`, err);

            // Re-lanzamos el error para que el componente pueda reaccionar si quiere
            return throwError(() => ({ ...err, userMessage }));
        })
    );
};
