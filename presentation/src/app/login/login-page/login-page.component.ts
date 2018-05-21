import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'

import { LoginService } from '../login.service';

/**
 * Componente responsável pela autenticação na aplicação.
 * 
 * @author Arthur Luckwu
 */
@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  public username: string;
  public password: string;
  public valorCaptcha: string;
  public loginErrorMessage: string = null;
  public isLogging: boolean = false;
  public captchaImage: string;
  public credentials = {
    email: null,
    senha: null,
    idCaptcha: null,
    valorCaptcha: null
  };

  constructor(
    private loginService: LoginService,
    private router: Router,
    // private alert: AlertService
  ) { }

  /**
   * Verifica se a aplicação já contém um token, direcionando para a página correta.
   */
  ngOnInit() {
    if (sessionStorage.getItem('token')) {
      this.router.navigate(['portador']);
    } else {
      this.getCaptcha()
    }
  }

  onAlertClose() {
    this.loginErrorMessage = null;
  }

  /**
   * Captura ação da tecla "enter" para submeter ao login.
   * 
   * @param event 
   */
  enterLogin(event) {
    // enter login
    if (event.keyCode === 13) {
      this.doLogin();
    }
  }

  /**
   * Realiza o login na aplicação.
   */
  async doLogin() {
    if (this.username && this.password) {
      try {
        this.credentials.email = this.username;
        this.credentials.senha = this.password;
        this.credentials.valorCaptcha = this.valorCaptcha;
        this.loginErrorMessage = null;
        this.isLogging = true;
        const user: any = await this.loginService.doLogin(this.credentials);
        this.router.navigate(['portador']);
      } catch (error) {
        this.loginErrorMessage = error.headers.get('x-error-msg');
        this.getCaptcha()
      } finally {
        // hide spinner
        this.isLogging = false;
      }
    } else {
      this.loginErrorMessage = "É necessário informar seu e-mail e sua senha."
    }
  }

  /**
   * Obtem os dados do captcha para exibir na tela de login.
   */
  async getCaptcha() {
    try {
      const captcha: any = await this.loginService.getCaptcha();
      this.captchaImage = captcha.imageData;
      this.credentials.idCaptcha = captcha.id;
      console.log(this.credentials);
    } catch (error) {
      // this.loginErrorMessage = error.headers.get('x-error-msg');
    } finally {
      // hide spinner
      this.isLogging = false;
    }
  }
}
