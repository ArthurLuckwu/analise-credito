import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ANALISEROUTING } from './credito.routing';
import { AlertService } from '../alert.service';
import { AnaliseComponent } from './analise/analise.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { TextMaskModule } from 'angular2-text-mask';
import { ClarityModule } from '@clr/angular';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreditoService } from './credito.service';
import { AuthGuardCredito } from './auth-guard.service';
import { Location } from '@angular/common';

/**
 * Módulo responsável pelas operações de análise de crédito.
 * 
 * @author Arthur Luckwu
 */
@NgModule({
  imports: [
    CommonModule,
    ANALISEROUTING,
    FormsModule,
    ReactiveFormsModule,
    ClarityModule,
    TextMaskModule,
    CurrencyMaskModule
  ],
  declarations: [AnaliseComponent],
  providers: [CreditoService, AuthGuardCredito]
})
export class CreditoModule { }
