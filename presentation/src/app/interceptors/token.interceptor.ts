import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

/**
 * Realiza a interceptação do token obtido ao realizar o login no sistema e o adiciona ao
 * header de todas as requisições http ao backend.
 * 
 * @author Arthur Luckwu
 */
@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token: any = sessionStorage.getItem('token');
    const headers: any = {'Content-Type': 'application/json'};

    if(token) {
        headers['x-auth-token'] = token;
    }

    request = request.clone({
        setHeaders: headers
    });

    return next.handle(request);
  }
}
