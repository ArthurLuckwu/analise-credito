import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ClarityModule } from '@clr/angular';
import { ConsultaPortadorComponent } from './consulta-portador/consulta-portador.component';
import { PortadorService } from './portador.service';
import { PORTADORROUTING } from './portador.routing';
import { CadastroPortadorComponent } from './cadastro-portador/cadastro-portador.component';
import { TextMaskModule } from 'angular2-text-mask';
import { CurrencyMaskModule } from "ng2-currency-mask";
import { DetalhesPortadorComponent } from './detalhes-portador/detalhes-portador.component';
import { AuthGuardPortador } from './auth-guard.service';

/**
 * Módulo responsável pelas operações de portador crédito.
 * 
 * @author Arthur Luckwu
 */
@NgModule({
  imports: [
    CommonModule,
    PORTADORROUTING,
    FormsModule,
    ReactiveFormsModule,
    ClarityModule,
    TextMaskModule,
    CurrencyMaskModule
  ],
  declarations: [CadastroPortadorComponent, ConsultaPortadorComponent, DetalhesPortadorComponent],
  providers: [PortadorService, AuthGuardPortador]

})
export class PortadorModule { }
