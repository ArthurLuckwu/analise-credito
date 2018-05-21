import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/operator/map';
import { ClrDatagridStateInterface } from '@clr/angular';
import ClrDatagridUtils from '../clrDatagridUtils';
import { CreateUpdateCreditoDTO } from './credito';

/**
 * Consumo de serviços referentes ao crédito.
 * 
 * @author Arthur Luckwu
 */
@Injectable()
export class CreditoService {

  constructor( @Inject('API_URL') private url: string, private http: HttpClient) { }

  /**
   * Obtém dados de uma análise em específico.
   * 
   * @param id 
   */
  get(id) {
    return new Promise((resolve, reject) => {
      const url: string = `${this.url}/credito/analise/${id}`;

      this.http.get(url)
        .subscribe(res => {
          resolve(res);
        }, error => {
          reject(error);
        });
    });
  }

  /**
   * Realiza o cadastro de uma análise de crédito.
   * 
   * @param id 
   * @param params 
   */
  add(id: number, params: CreateUpdateCreditoDTO) {
    return new Promise((resolve, reject) => {
      const url: string = `${this.url}/credito/analisarCredito/${id}`;

      this.http.put(url, params)
        .subscribe(res => {
          resolve(res);
        }, error => {
          reject(error);
        });
    });
  }

}
