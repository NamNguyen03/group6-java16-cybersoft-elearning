import {
    HttpEvent,
    HttpHandler,
    HttpRequest,
    HttpErrorResponse,
    HttpInterceptor,
} from '@angular/common/http';
import { catchError} from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { UserService } from '../user/user.service';


@Injectable({
    providedIn: 'root',
})
export class ExceptionInterceptor implements HttpInterceptor {

    constructor(private _router: Router,private userService: UserService){}

    intercept(
        request: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            catchError((error: HttpErrorResponse) => {
                if (error.status === 400) {    
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: error.error.errors
                      })
                }
                return throwError("");
            })
        );
    }
    
}