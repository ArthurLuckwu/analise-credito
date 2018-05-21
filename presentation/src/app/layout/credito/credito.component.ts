import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../login/login.service';

/**
 * Componente responsável pelo layout do módulo de crédito.
 * 
 * @author Arthur Luckwu
 */
@Component({
  selector: 'app-credito',
  templateUrl: './credito.component.html',
  styleUrls: ['./credito.component.scss']
})
export class CreditoComponent implements OnInit {
  public usuarioLogado: any; 

  constructor(
    private router: Router,
    private loginService: LoginService
  ) { 
    if (sessionStorage.getItem('user')) {
      this.usuarioLogado = JSON.parse(sessionStorage.getItem('user')).nome;
    }
  }

  ngOnInit() {
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
}
