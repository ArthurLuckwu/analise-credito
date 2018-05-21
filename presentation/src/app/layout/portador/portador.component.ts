import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../login/login.service';

/**
 * Componente responsável pelo layout do módulo de portador.
 * 
 * @author Arthur Luckwu
 */
@Component({
  selector: 'app-portador',
  templateUrl: './portador.component.html',
  styleUrls: ['./portador.component.scss']
})
export class PortadorComponent {
  public usuarioLogado: any; 
  public usuarioPerfil: any; 

  constructor(
    private router: Router,
    private loginService: LoginService
  ) { 
    if (sessionStorage.getItem('user')) {
      this.usuarioLogado = JSON.parse(sessionStorage.getItem('user')).nome;
      this.usuarioPerfil = JSON.parse(sessionStorage.getItem('user')).perfil.id;
    }
  }

  /**
   * Realiza logout do usuário na aplicação.
   */
  async logout() {
    try {
      await this.loginService.logout();
    } finally {
      sessionStorage.removeItem('token');
      sessionStorage.removeItem('user');
      this.router.navigate(['login']);
    }
  }

  /**
   * Verifica se o usuário logado tem permissão para acessar determinarda funcionalidade,
   * exibindo ou não essa funcionalidade para o usuaŕio.
   */
  permissao() {
    if (this.usuarioPerfil == 1) {
      return false;
    } else {
      return true;
    }
  }

}
