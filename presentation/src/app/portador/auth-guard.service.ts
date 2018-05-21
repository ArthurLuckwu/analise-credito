import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivate } from '@angular/router';
import { Location } from '@angular/common';

import { AlertService } from '../alert.service';

/**
 * Responsável pelo controle de acesso nas páginas de portador de crédito de acordo 
 * com perfil de usuário.
 * 
 * @author Arthur Luckwu
 */
@Injectable()
export class AuthGuardPortador implements CanActivate {

  constructor(
    private alert: AlertService,
    private router: Router,
    private location: Location
  ) { }

  canActivate() {
    const token = sessionStorage.getItem('token');
    const perfil = JSON.parse(sessionStorage.getItem('user')).perfil.id;
    if (token) {
      if (perfil == 2) {
        return true;
      } else {
        this.alert.error("Você não tem permissão para acessar esta página.");
        this.location.back();
        return false;
      }
    } else {
      this.router.navigate(['login']);
      this.alert.error("É necessário efetuar o login para realizar esta ação.");
      return false;
    }
  }
}
