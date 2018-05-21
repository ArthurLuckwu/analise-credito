import { Injectable } from '@angular/core';
import { ToasterService } from 'angular2-toaster';

@Injectable()
export class AlertService {
  private toasterService: ToasterService;

  constructor(
    toasterService: ToasterService
  ) {
    this.toasterService = toasterService;
  }

  error(msg: string, title?: string) {
    this.toasterService.pop('error', title, msg);
  }

  success(msg: string, title?: string) {
    this.toasterService.pop('success', title, msg);
  }

  info(msg: string, title?: string) {
    this.toasterService.pop('info', title, msg);
  }
 
}
