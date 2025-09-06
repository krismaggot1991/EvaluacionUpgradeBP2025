import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

/**
 * Interceptor de errores HTTP
 * - Centraliza el manejo de errores
 * - Normaliza un mensaje amigable en `userMessage`
 */
export const errorInterceptor: HttpInterceptorFn = (req, next) => {
    return next(req).pipe(
        catchError((err: HttpErrorResponse) => {
            let userMessage = 'Error inesperado';

            // Prioridad a mensajes del backend si existen
            if (err?.error?.message && typeof err.error.message === 'string') {
                userMessage = err.error.message;
            } else if (err.status === 0) {
                userMessage = 'No se pudo conectar con el servidor';
            } else if (err.status >= 500) {
                userMessage = 'Error interno del servidor';
            } else if (err.status === 404) {
                userMessage = 'Recurso no encontrado';
            } else if (err.status === 400) {
                userMessage = 'Solicitud inválida';
            } else if (err.status === 401) {
                userMessage = 'Sesión no autorizada';
                // Si tienes login, podrías redirigir aquí usando Router:
                // const router = inject(Router);
                // router.navigateByUrl('/login');
            }

            // Log técnico
            console.error(`HTTP ${err.status}`, err);

            // Re-lanzamos el error decorado para que el componente pueda mostrarlo
            return throwError(() => ({ ...err, userMessage }));
        })
    );
};
