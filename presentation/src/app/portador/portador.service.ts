import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/operator/map';
import { ClrDatagridStateInterface } from '@clr/angular';
import ClrDatagridUtils from '../clrDatagridUtils';
import { FindPortadorDTO, CreateUpdatePortadorDTO } from './portador';

/**
 * Consumo de serviços referentes a portador de crédito.
 * 
 * @author Arthur Luckwu
 */
@Injectable()
export class PortadorService {

  constructor( @Inject('API_URL') private url: string, private http: HttpClient) { }

  /**
   * Realiza a consulta de portador de crédito de acordo com os parametros passados.
   * 
   * @param searchParams 
   * @param state 
   */
  search(searchParams: FindPortadorDTO, state: ClrDatagridStateInterface) {
    let queryParams = ClrDatagridUtils.stateToQueryParams(state);
    return new Promise((resolve, reject) => {
      this.http.post(`${this.url}/credito/listPortador`, searchParams, { params: queryParams })
        .subscribe(data => {
          resolve(data);
        }, error => {
          reject(error);
        });
    });
  }

  /**
   * Obtém dados de um portador em específico.
   * 
   * @param id 
   */
  get(id) {
    return new Promise((resolve, reject) => {
      const url: string = `${this.url}/credito/portador/${id}`;

      this.http.get(url)
        .subscribe(res => {
          resolve(res);
        }, error => {
          reject(error);
        });
    });
  }

  /**
   * Realiza o cadastro de um portador de crédito.
   * 
   * @param id 
   * @param params 
   */
  add(params: CreateUpdatePortadorDTO) {
    return new Promise((resolve, reject) => {
      const url: string = `${this.url}/credito/cadastrarPortadorCredito`;

      this.http.post(url, params)
        .subscribe(res => {
          resolve(res);
        }, error => {
          reject(error);
        });
    });
  }

}
