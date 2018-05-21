import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ClarityModule } from '@clr/angular';
import { ToasterModule, ToasterService } from "angular2-toaster";
import { environment } from "environments/environment";
import { AlertService } from "./alert.service";
import { AppComponent } from './app.component';
import { ROUTING } from "./app.routing";
import { LayoutModule } from "./layout/layout.module";
import { LoginModule } from "./login/login.module";
import { AuthGuard } from "./auth-guard.service";
import { TokenInterceptor } from "./interceptors/token.interceptor";
import { ErrorInterceptor } from "./interceptors/error.interceptor";
import { PortadorModule } from "./portador/portador.module";
import { CreditoModule } from "./credito/credito.module";

@NgModule({
    declarations: [
        AppComponent,
    ],
    imports: [
        BrowserAnimationsModule,
        BrowserModule,
        FormsModule,
        HttpModule,
        HttpClientModule,
        ClarityModule,
        ROUTING,
        LayoutModule,
        PortadorModule,
        CreditoModule,
        LoginModule,
        ToasterModule
    ],
    providers: [
        AuthGuard,
        AlertService,
        ToasterService,
        // MonitoringAlertService,
        // DataSharer,
        // {provide: OWL_DATE_TIME_FORMATS, useValue: DATEPICKER_MOMENT_FORMATS},
        { provide: 'API_URL', useValue: environment.apiUrl },
        // { provide: LocationStrategy, useClass: HashLocationStrategy },
        { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
      ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
