import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PortadorService } from '../../portador/portador.service';
import { ResponsePortadorCreditoDTO } from '../../portador/portador';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { conformToMask } from 'angular2-text-mask';
import { CreateUpdateCreditoDTO } from '../credito';
import { AlertService } from '../../alert.service';
import { CreditoService } from '../credito.service';
import { Location } from '@angular/common';

/**
 * Componente responsável pela análise de crédito.
 * 
 * @author Arthur Luckwu
 */
@Component({
  selector: 'app-analise',
  templateUrl: './analise.component.html',
  styleUrls: ['./analise.component.scss']
})
export class AnaliseComponent implements OnInit {

  private id: number = null;
  public portador = new ResponsePortadorCreditoDTO();
  public form: FormGroup;
  public aprovado: boolean = false;

  public cepMask = [/\d/, /\d/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/];
  public cpfMask = [/\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '-', /\d/, /\d/];

  constructor(
    private route: ActivatedRoute,
    private portadorService: PortadorService,
    private creditoService: CreditoService,
    private alert: AlertService,
    private router: Router,
    private formBuilder: FormBuilder,
    private location: Location
  ) {
    this.form = formBuilder.group({
      nome: new FormControl({ value: null, disabled: true }, [Validators.required, Validators.maxLength(80), Validators.minLength(4)]),
      email: new FormControl({ value: null, disabled: true }, [Validators.required, Validators.maxLength(100)]),
      cpf: new FormControl({ value: null, disabled: true }, [Validators.required, Validators.maxLength(14)]),
      rua: new FormControl({ value: null, disabled: true }, [Validators.required, Validators.maxLength(100)]),
      bairro: new FormControl({ value: null, disabled: true }, [Validators.required, Validators.maxLength(60)]),
      numero: new FormControl({ value: null, disabled: true }, [Validators.required, Validators.maxLength(6)]),
      complemento: new FormControl({ value: null, disabled: true }, Validators.maxLength(100)),
      cep: new FormControl({ value: null, disabled: true }, [Validators.required, Validators.maxLength(9)]),
      municipio: new FormControl({ value: null, disabled: true }, [Validators.required, Validators.maxLength(100)]),
      uf: new FormControl({ value: null, disabled: true }, [Validators.required, Validators.maxLength(2), Validators.minLength(2)]),
      dividaSerasa: new FormControl({ value: null, disabled: true }, []),
      dividaSpc: new FormControl({ value: null, disabled: true }, []),
      dividaCartorio: new FormControl({ value: null, disabled: true }, []),
      creditoConcedido: new FormControl(null, []),
      aprovado: new FormControl(null, [])
    });
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadDetails(params.id));
  }

  /**
   * Carrega os dados de um portador em específico e preenche os campos do form para
   * que sejam analisados.
   * 
   * @param id 
   */
  async loadDetails(id) {
    if (id) {
      try {
        const portadorCredito: any = await this.portadorService.get(id);
        if (!portadorCredito.analisado) {
          this.id = portadorCredito.id;
          // this.portador = portadorCredito;
          this.form.controls.nome.setValue(portadorCredito.nome);
          this.form.controls.cpf.setValue(conformToMask(portadorCredito.cpf, this.cpfMask, { guide: false }).conformedValue);
          this.form.controls.email.setValue(portadorCredito.email);
          this.form.controls.rua.setValue(portadorCredito.rua);
          this.form.controls.bairro.setValue(portadorCredito.bairro);
          this.form.controls.numero.setValue(portadorCredito.numero);
          this.form.controls.complemento.setValue(portadorCredito.complemento);
          this.form.controls.cep.setValue(conformToMask(portadorCredito.cep, this.cepMask, { guide: false }).conformedValue);
          this.form.controls.municipio.setValue(portadorCredito.municipio);
          this.form.controls.uf.setValue(portadorCredito.uf);
          this.form.controls.dividaSerasa.setValue(portadorCredito.dividaSerasa);
          this.form.controls.dividaSpc.setValue(portadorCredito.dividaSpc);
          this.form.controls.dividaCartorio.setValue(portadorCredito.dividaCartorio);
        } else {
          this.alert.error("Já há uma Análise de Crédito para esta proposta.");
          this.location.back();
        }
      } catch (error) {
        this.location.back();
      }
    }
  }

  /**
   * Salva a análise de crédito.
   */
  async save() {
    const analise = new CreateUpdateCreditoDTO();
    
    if (this.form.controls.aprovado.value != null) {
      analise.aprovado = this.form.controls.aprovado.value;
    }
    if (this.form.controls.creditoConcedido.value) {
      analise.creditoConcedido = this.form.controls.creditoConcedido.value;
    }

    try {
      await this.creditoService.add(this.id, analise);
      this.router.navigate(['/portador']);
      this.alert.success('Análise de Crédito salva com sucesso.');
    } catch (error) {
    }
  }

  /**
   * Verifica se o crédito está sendo aprovado e altera propriedades do campo "credito concedido". 
   */
  checkAprovado() {
    if (this.form.controls.aprovado.value != null) {
      this.aprovado = this.form.controls.aprovado.value;
    }
    if (this.aprovado) {
      return false;
    } else {
      this.form.controls.creditoConcedido.setValue(null);
      return true;
    }
  }

}
