import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import { conformToMask } from 'text-mask-core/dist/textMaskCore';
import { PortadorService } from '../portador.service';
import { AlertService } from '../../alert.service';
import { CreateUpdateCreditoDTO, ResponseCreditoDTO } from '../../credito/credito';
import { CreateUpdatePortadorDTO, ResponsePortadorCreditoDTO } from '../portador';
import { CreditoService } from '../../credito/credito.service';

/**
 * Componente responsável pelos detalhes do portador de crédito.
 * 
 * @author Arthur Luckwu
 */
@Component({
  selector: 'app-detalhes-portador',
  templateUrl: './detalhes-portador.component.html',
  styleUrls: ['./detalhes-portador.component.scss']
})
export class DetalhesPortadorComponent implements OnInit {

  public cepMask = [ /\d/, /\d/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/];
  public cpfMask = [ /\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '-', /\d/, /\d/];
  public form: FormGroup;
  public usuarioPerfil: any; 
  public portadorCredito: ResponsePortadorCreditoDTO;
  public situacao: string;
  public aprovado: boolean;

  private id: number = null;
  public gatewayFirmwareList: any;
  
  constructor(
    private creditoService: CreditoService,
    private router: Router,
    private alert: AlertService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
  ) {
    this.form = formBuilder.group({
      nome: new FormControl({value: null, disabled: true}, [Validators.required, Validators.maxLength(80),  Validators.minLength(4)]),
      email: new FormControl({value: null, disabled: true}, [Validators.required, Validators.maxLength(100)]),
      cpf: new FormControl({value: null, disabled: true}, [Validators.required,  Validators.maxLength(14)]),
      rua: new FormControl({value: null, disabled: true}, [Validators.required, Validators.maxLength(100)]),
      bairro: new FormControl({value: null, disabled: true}, [Validators.required, Validators.maxLength(60)]),
      numero: new FormControl({value: null, disabled: true}, [Validators.required, Validators.maxLength(6)]),
      complemento: new FormControl({value: null, disabled: true}, Validators.maxLength(100)),
      cep: new FormControl({value: null, disabled: true}, [Validators.required, Validators.maxLength(9)]),
      municipio: new FormControl({value: null, disabled: true}, [Validators.required, Validators.maxLength(100)]),
      uf: new FormControl({value: null, disabled: true}, [Validators.required, Validators.maxLength(2), Validators.minLength(2)]),
      dividaSerasa: new FormControl({value: null, disabled: true}, []),
      dividaSpc: new FormControl({value: null, disabled: true}, []),
      dividaCartorio: new FormControl({value: null, disabled: true}, []),
      aprovado: new FormControl({value: null, disabled: true}, []),
      creditoConcedido: new FormControl({value: null, disabled: true}, [])
    });
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.loadDetails(params.id));
  }
  
  /**
   * Carrega os dados de um portador em específico e preenche os campos do form.
   * 
   * @param id 
   */
  async loadDetails(id) {
    if (id) {
      try {
        const analiseCredito: any = await this.creditoService.get(id);
        if (analiseCredito) {
          this.portadorCredito = analiseCredito.portadorCredito;

          this.form.controls.nome.setValue(this.portadorCredito.nome);
          this.form.controls.cpf.setValue(conformToMask(this.portadorCredito.cpf, this.cpfMask, { guide: false }).conformedValue);
          this.form.controls.email.setValue(this.portadorCredito.email);
          this.form.controls.rua.setValue(this.portadorCredito.rua);
          this.form.controls.bairro.setValue(this.portadorCredito.bairro);
          this.form.controls.numero.setValue(this.portadorCredito.numero);
          this.form.controls.complemento.setValue(this.portadorCredito.complemento);
          this.form.controls.cep.setValue(conformToMask(this.portadorCredito.cep, this.cepMask, { guide: false }).conformedValue);
          this.form.controls.municipio.setValue(this.portadorCredito.municipio);
          this.form.controls.uf.setValue(this.portadorCredito.uf);
          this.form.controls.dividaSerasa.setValue(this.portadorCredito.dividaSerasa);
          this.form.controls.dividaSpc.setValue(this.portadorCredito.dividaSpc);
          this.form.controls.dividaCartorio.setValue(this.portadorCredito.dividaCartorio);
          this.form.controls.aprovado.setValue(this.portadorCredito.aprovado);
          this.form.controls.creditoConcedido.setValue(analiseCredito.creditoConcedido);

          if (this.portadorCredito.aprovado) {
            this.aprovado = true;
            this.situacao = 'APROVADO';
          } else {
            this.aprovado = false;
            this.situacao = 'REPROVADO';
          }

        }
      } catch (error) {
      }
    }
  }

}
