import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';
import { ResponseDadoGrafico } from '../../module/inteface/dados-para-grafico';

@Injectable({
  providedIn: 'root',
})
export class GraficoService {
  constructor(private http: HttpClient) {}

  obterGraficoStatus(): Observable<ResponseDadoGrafico> {
    const url = `${environment.URL_BASE + environment.GRAFICO_STATUS}`;
    return this.http.get<ResponseDadoGrafico>(`${url}`);
  }

  obterGraficoEscolares(): Observable<ResponseDadoGrafico> {
    const url = `${environment.URL_BASE + environment.GRAFICO_ESCOLARIDADE}`;
    return this.http.get<ResponseDadoGrafico>(`${url}`);
  }
}
