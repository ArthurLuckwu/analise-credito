/*
 * Copyright (c) 2016 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */
import { ModuleWithProviders } from '@angular/core/src/metadata/ng_module';
import { Routes, RouterModule } from '@angular/router';
import { CreditoComponent } from '../layout/credito/credito.component';
import { AnaliseComponent } from './analise/analise.component';
import { AuthGuardCredito } from './auth-guard.service';

export const ROUTES: Routes = [
    {
        path: 'credito',
        component: CreditoComponent,
        canActivate: [AuthGuardCredito],
        children: [
          { path: '', redirectTo: 'analise', pathMatch: 'full' },
          { path: 'analise/:id', component: AnaliseComponent },
        ]
      }
];

export const ANALISEROUTING: ModuleWithProviders = RouterModule.forRoot(ROUTES);
