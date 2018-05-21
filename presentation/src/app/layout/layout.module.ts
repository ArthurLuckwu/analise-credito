import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PortadorComponent } from './portador/portador.component';
import { RouterModule } from '@angular/router';
import { CreditoComponent } from './credito/credito.component';
import { ClarityModule } from '@clr/angular';

/**
 * Módulo responsável pelos layouts da aplicação.
 * 
 * @author Arthur Luckwu
 */
@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    ClarityModule
  ],
  declarations: [PortadorComponent, CreditoComponent]
})
export class LayoutModule { }
