import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

import { Router } from '@angular/router';

import { AlertService } from '../alert.service';

/**
 * Realiza a interceptação de erros lançados pelas APIs do backend, as trata, 
 * e as lança na tela ao usuário.
 * 
 * @author Arthur Luckwu
 */
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(
    private alert: AlertService,
    private router: Router
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next
      .handle(request)
      .catch(response => {
        if (response instanceof HttpErrorResponse) {
          const errorCode = Number(response.headers.get('errorCode'));
          const errorMsg = response.headers.get('errorMsg');
          const errorField = response.headers.get('errorField');
          switch (errorCode) {
            case 0: // Unknown Error
              if (errorMsg !== '') {
                this.alert.error(errorMsg);
              } else {
                this.alert.error('Ocorreu um erro inesperado. Por favor, tente novamente em alguns instantes, caso o problema persista entre em contato com o suporte.');
              }
              break;
            // case 1: // Field Validation Error
            //   const errorMessages = errorMsg.split(";");
            //   const fieldsWithErrors = errorField.split(";");
            //   const errors = [];

            //   for (let i = 0; i < fieldsWithErrors.length; i += 1) {
            //     const field = fieldsWithErrors[i];
            //     const message = errorMessages[i];
            //     errors.push({ field: { 'serverValidation': message } });
            //   }
            //   break;
            case 20: // Unauthorized
              if (sessionStorage.getItem('token')) {
                // Doesn't have permission to execute this specifc action
                this.alert.error(errorMsg);
              } else {
                // Not logged, needs to login
                this.router.navigate(['login']);
                this.alert.error(errorMsg);
              }
              break;
            case 21: // Bad Credentials
              if (errorMsg !== '') {
                this.alert.error(errorMsg);
              } else {
                this.alert.error('Ocorreu um erro inesperado. Por favor, tente novamente em alguns instantes, caso o problema persista entre em contato com o suporte.');
              }
            case 25: // Weak Password
              // Do nothing, the error should be handled by the calling function
              break;
            case 27: // Expired Session
              sessionStorage.removeItem('token');
              sessionStorage.removeItem('user');
              this.router.navigate(['login']);
              this.alert.error(errorMsg);
              break;
            default: // All other errors doesn't need special behavior
              this.alert.error(errorMsg);
              break;
          }
        }

        return Observable.throw(response);
      });
  }
}
