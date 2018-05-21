import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivate } from '@angular/router';

import { AlertService } from './alert.service';

/**
 * Responsável pelo controle de acesso nas páginas da aplicação de modo geral.
 * 
 * @author Arthur Luckwu
 */
@Injectable()
export class AuthGuard implements CanActivate {

  constructor(
    private alert: AlertService,
    private router: Router,
  ) { }

  canActivate() {
    const token = sessionStorage.getItem('token');
    if (token) {
      return true;
    } else {
      this.router.navigate(['login']);
      this.alert.error("É necessário efetuar o login para realizar esta ação.");
      return false;
    }
  }
}
