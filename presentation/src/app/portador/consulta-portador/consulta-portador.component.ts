import { Component, OnInit } from '@angular/core';
import { PortadorService } from '../portador.service';
import { Router } from '@angular/router';
import { ClrDatagridStateInterface } from '@clr/angular';
import { FindPortadorDTO, ResponsePortadorCreditoDTO } from '../portador';
import { conformToMask } from 'angular2-text-mask';

/**
 * Componente responsável pela consulta de portadores de crédito.
 * 
 * @author Arthur Luckwu
 */
@Component({
  selector: 'app-consulta-portador',
  templateUrl: './consulta-portador.component.html',
  styleUrls: ['./consulta-portador.component.scss']
})
export class ConsultaPortadorComponent implements OnInit {

  private dataGridState: ClrDatagridStateInterface;
  public searchParams = new FindPortadorDTO();
  public loading: boolean = true;
  public total: number;
  public responsePortador: ResponsePortadorCreditoDTO;
  public selectedItem: any = null;
  public usuarioPerfil: any; 

  public cpfMask = [/\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '-', /\d/, /\d/];

  constructor(
    private portadorService: PortadorService,
    private router: Router
  ) {
    if (sessionStorage.getItem('user')) {
      this.usuarioPerfil = JSON.parse(sessionStorage.getItem('user')).perfil.id;
    }
  }

  ngOnInit() {
  }

  /**
   * Realiza a consulta dos portadores de crédito.
   * 
   * @param state 
   */
  async search(state?: ClrDatagridStateInterface) {

    if (state) {
      this.dataGridState = state;
    } 

    this.loading = true;

    if (this.searchParams.cpf != null) {
      this.searchParams.cpf = this.searchParams.cpf.replace(/[^\d]+/g, '');
    }

    try {
      this.dataGridState = {};
      this.dataGridState.sort = {
        by: "nome",
        reverse: false
      }

      this.searchParams.analisado = true;
      if (this.usuarioPerfil == 1) {
        this.searchParams.analisado = false;
      }
      const response: any = await this.portadorService.search(this.searchParams, this.dataGridState);
      this.responsePortador = response.content;
      this.total = response.totalElements;
    } finally {
      this.loading = false;
      this.selectedItem = null;
    }
  }

  /**
   * Redireciona à tela de análise de crédito.
   */
  analisar() {
    this.router.navigate(['/credito/analise/' + this.selectedItem.id]);
    this.selectedItem = null;
  }

  /**
   * Redireciona à tela de detalhes do portador.
   */
  detalhes() {
    this.router.navigate(['/portador/detalhes-portador/' + this.selectedItem.id]);
    this.selectedItem = null;
  }

  /**
   * Verifica se o usuário logado tem permissão para acessar a funcionalidade de 
   * análise de crédito, exibindo ou não essa funcionalidade para o usuaŕio.
   */
  permissaoAnalisar() {
    if (this.usuarioPerfil == 1) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Captura ação da tecla "enter" para realizar consulta.
   * 
   * @param event 
   */
  enterSearch(event) {
    console.log('opa');
    
    // enter search
    if (event.keyCode === 13) {
      this.search(event);
    }
  }

  /**
   * Realiza a formatação do CPF.
   * 
   * @param cpf 
   */
  formatCpf(cpf: String) {
    return conformToMask(cpf, this.cpfMask, { guide: false }).conformedValue;
  }
}
