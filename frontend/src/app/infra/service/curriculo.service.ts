import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';
import { CadastroCurriculoModel } from '../../module/model/cadastro-curriculo-model';
import { AlteraStatusModel } from '../../module/model/altera-status-model';
import { CompotenciaModal } from '../../module/model/competencia-moral';

@Injectable({
  providedIn: 'root',
})
export class CurriculoService {
  constructor(private http: HttpClient) {}

  cadastroCurriculo(
    curriculo: CadastroCurriculoModel
  ): Observable<CadastroCurriculoModel> {
    const userPayload = {
      ...curriculo,
      escolaridadeEnum: curriculo.escolaridadeEnum,
      competencia: curriculo.competencia.map((comp: CompotenciaModal) => ({
        competenciaEnum: comp.competenciaEnum,
        nivelEnum: comp.nivelEnum,
      })),
    };    
    const url = `${environment.URL_FORM}`;
    return this.http.post<CadastroCurriculoModel>(`${url}`, userPayload);
  }

  alteraStatusCurriculo(
    id: number,
    curriculo: AlteraStatusModel
  ): Observable<AlteraStatusModel> {
    const url = `${environment.URL_FORM + environment.ALTERA_ADMIN}`;
    return this.http.put<AlteraStatusModel>(`${url}/${id}`, curriculo);
  }

  obterCurriculoPorEmail(email: string): Observable<any> {
    const url = `${environment.URL_FORM}`;
    return this.http.get<any>(`${url}/${email}`);
  }

  obterListaCurriculo(): Observable<any> {
    const url = `${environment.URL_FORM}`;
    return this.http.get<any>(url);
  }
}
