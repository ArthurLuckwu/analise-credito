import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import { conformToMask } from 'text-mask-core/dist/textMaskCore';
import { PortadorService } from '../portador.service';
import { AlertService } from '../../alert.service';
import { CreateUpdateCreditoDTO } from '../../credito/credito';
import { CreateUpdatePortadorDTO } from '../portador';

/**
 * Componente responsável pelo cadastro de portadores.
 * 
 * @author Arthur Luckwu
 */
@Component({
  selector: 'app-cadastro-portador',
  templateUrl: './cadastro-portador.component.html',
  styleUrls: ['./cadastro-portador.component.scss']
})
export class CadastroPortadorComponent implements OnInit {

  public cepMask = [ /\d/, /\d/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/];
  public cpfMask = [ /\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '-', /\d/, /\d/];
  public form: FormGroup;

  private id: number = null;
  public gatewayFirmwareList: any;
  
  constructor(
    private portadorService: PortadorService,
    private router: Router,
    private alert: AlertService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
  ) {
    this.form = formBuilder.group({
      nome: new FormControl(null, [Validators.required, Validators.maxLength(80),  Validators.minLength(4)]),
      email: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
      cpf: new FormControl(null, [Validators.required,  Validators.maxLength(14)]),
      rua: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
      bairro: new FormControl(null, [Validators.required, Validators.maxLength(60)]),
      numero: new FormControl(null, [Validators.required, Validators.maxLength(6)]),
      complemento: new FormControl(null, Validators.maxLength(100)),
      cep: new FormControl(null, [Validators.required, Validators.maxLength(9)]),
      municipio: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
      uf: new FormControl(null, [Validators.required, Validators.maxLength(2), Validators.minLength(2)]),
      dividaSerasa: undefined,
      dividaSpc: undefined,
      dividaCartorio: undefined
    });
  }

  ngOnInit() {
  }
  
  /**
   * Realiza o cadastro de um portador de crédito.
   */
  async save() {
    var dividaSerasa = (<HTMLInputElement>document.getElementById('dividaSerasa')).value;
    var dividaSpc = (<HTMLInputElement>document.getElementById('dividaSpc')).value;
    var dividaCartorio = (<HTMLInputElement>document.getElementById('dividaCartorio')).value;
    
    const portador = new CreateUpdatePortadorDTO();
    portador.nome = this.form.controls.nome.value;
    portador.cpf = this.form.controls.cpf.value.replace(/[^\d]+/g, '');
    portador.email = this.form.controls.email.value;
    portador.rua = this.form.controls.rua.value;
    portador.bairro = this.form.controls.bairro.value;
    portador.numero = this.form.controls.numero.value;
    portador.complemento = this.form.controls.complemento.value;
    portador.cep = this.form.controls.cep.value;
    portador.municipio = this.form.controls.municipio.value;
    portador.uf = this.form.controls.uf.value;
    portador.dividaSerasa = dividaSerasa.split('.').join('').replace(',','.').replace('R$', '');
    portador.dividaSpc = dividaSpc.split('.').join('').replace(',','.').replace('R$', '');
    portador.dividaCartorio = dividaCartorio.split('.').join('').replace(',','.').replace('R$', '');

    try {
      await this.portadorService.add(portador);
      this.router.navigate(['/portador']);
      this.alert.success('Portador de Crédito salvo com sucesso.');
    } catch (error) {
    }
  }

}
