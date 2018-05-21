import { SharedModule } from '../shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClarityModule } from '@clr/angular';

import { LoginRoutingModule } from './login-routing.module';
import { LoginPageComponent } from './login-page/login-page.component';

import { LoginService } from './login.service';

/**
 * Módulo responsável pelas operações de autenticação na aplicação.
 * 
 * @author Arthur Luckwu
 */
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    LoginRoutingModule,
    ClarityModule,
    SharedModule
  ],
  declarations: [LoginPageComponent],
  providers: [LoginService]
})
export class LoginModule { }
