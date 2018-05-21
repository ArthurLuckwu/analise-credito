/*
 * Copyright (c) 2016 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */
import { ModuleWithProviders } from '@angular/core/src/metadata/ng_module';
import { Routes, RouterModule } from '@angular/router';
import { PortadorComponent } from '../layout/portador/portador.component';
import { ConsultaPortadorComponent } from './consulta-portador/consulta-portador.component';
import { CadastroPortadorComponent } from './cadastro-portador/cadastro-portador.component';
import { DetalhesPortadorComponent } from './detalhes-portador/detalhes-portador.component';
import { AuthGuard } from '../auth-guard.service';
import { AuthGuardPortador } from './auth-guard.service';


export const ROUTES: Routes = [
    {
        path: 'portador',
        component: PortadorComponent,
        canActivate: [AuthGuard],
        children: [
          { path: '', redirectTo: 'consulta-portador', pathMatch: 'full' },
          { path: 'consulta-portador', component: ConsultaPortadorComponent },
          { path: 'cadastro-portador', component: CadastroPortadorComponent, canActivate: [AuthGuardPortador] },
          { path: 'detalhes-portador/:id', component: DetalhesPortadorComponent, canActivate: [AuthGuardPortador] },
        ]
      }
];

export const PORTADORROUTING: ModuleWithProviders = RouterModule.forRoot(ROUTES);
