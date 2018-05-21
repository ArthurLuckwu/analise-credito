import { HttpClient } from '@angular/common/http';
import { Injectable, Inject } from '@angular/core';
import { Http, RequestOptions, Headers } from '@angular/http';
import 'rxjs/add/operator/map';

/**
 * Consumo de serviços referentes à autenticação na aplicação.
 * 
 * @author Arthur Luckwu
 */
@Injectable()
export class LoginService {

  constructor( @Inject('API_URL') private url: string, private http: HttpClient) { }

  /**
   * Realiza o login na aplicação.
   * 
   * @param credentials 
   */
  doLogin(credentials) {
    return new Promise((resolve, reject) => {

      const url: string = `${this.url}/auth/login`;
      this.http.post(url, credentials, { observe: 'response' })
        .subscribe(response => {
          sessionStorage.setItem('token', response.headers.get("x-auth-token"));
          sessionStorage.setItem('user', JSON.stringify(response.body));
          resolve(response.body);
        }, error => {
          reject(error);
        });
    });
  }

  /**
   * Obtém os dados de captcha.
   */
  getCaptcha() {
    return new Promise((resolve, reject) => {
      const url: string = `${this.url}/auth/captcha`;
      this.http.get(url)
        .subscribe(res => {
          resolve(res);
        }, error => {
          reject(error);
        });
    });
  }

  /**
   * Realiza logout da aplicação.
   */
  logout() {
    return new Promise((resolve, reject) => {
      this.http.post(`${this.url}/auth/logout`, {}, { responseType: 'text' })
        .subscribe(response => {
          resolve(response);
        }, error => {
          reject(error);
        });
    });
  }
}
