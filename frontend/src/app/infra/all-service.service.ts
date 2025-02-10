import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { CadastroCurriculoModel } from '../module/model/cadastro-curriculo-model';
import { CadastroUserModel } from '../module/model/cadastro-user-model';
import { GetEscolaridade } from '../module/model/get-escolaridade';
import { GetStatus } from '../module/model/get-status';
import { LoginModel } from '../module/model/login-model';
import { EscolaridadeResponse, StatusResponse } from '../module/model/dados-para-grafico';

@Injectable({
  providedIn: 'root'
})
export class AllServiceService {
  constructor(private http: HttpClient) {}

  cadastroCurriculo(curriculo: CadastroCurriculoModel): Observable<CadastroCurriculoModel> {
    const url = `${environment.URL_FORM}`;
    return this.http.post<CadastroCurriculoModel>(`${url}`,curriculo);
  }

  alteraCurriculo(id: number): Observable<CadastroCurriculoModel> {
    const url = `${environment.URL_FORM + environment.ALTERA_USER}`;
    return this.http.put<CadastroCurriculoModel>(`${url}/${id}`,id);
  }

  alteraStatus(id: number): Observable<CadastroCurriculoModel> {
    const url = `${environment.URL_FORM + environment.ALTERA_ADMIN}`;
    return this.http.put<CadastroCurriculoModel>(`${url}/${id}`,id);
  }

  alteraStatusCurriculo(id:number,user:CadastroCurriculoModel): Observable<CadastroCurriculoModel> {
    const url = `${environment.URL_FORM + environment.ALTERA_ADMIN}`;
    return this.http.put<CadastroCurriculoModel>(`${url}/${id}`,user);
  }

  obterUser(id: number): Observable<CadastroCurriculoModel> {
    const url = `${environment.URL_FORM}`;
    return this.http.get<CadastroCurriculoModel>(`${url}/${id}`);
  }

  obterPaginacao(): Observable<{content:CadastroCurriculoModel[]}> {
    const url = `${environment.URL_FORM}`;
    return this.http.get<{content:CadastroCurriculoModel[]}>(`${url}`);
  }

  cadastroUser(user: CadastroUserModel): Observable<CadastroUserModel> {
    const url = `${environment.URL_USER + environment.CADASTRO}`;

    return this.http.post<CadastroUserModel>(`${url}`,user);
  }

  login(login: LoginModel): Observable<LoginModel> {
    const url = `${environment.URL + environment.LOGIN}`;
    return this.http.post<LoginModel>(`${url}`,login);
  }

  obterGraficoStatus(): Observable<StatusResponse> {
    const url = `${environment.URL + environment.GRAFICO_STATUS}`;
    return this.http.get<StatusResponse>(`${url}`);
  }

  obterGraficoEscolares(): Observable<EscolaridadeResponse> {
    const url = `${environment.URL + environment.GRAFICO_ESCOLARIDADE}`;
    return this.http.get<EscolaridadeResponse>(`${url}`);
  }

  obterCurriculoPorEmail(email: string): Observable<any> {
    const url = `${environment.URL_USER}`;
    return this.http.get<any>(`${url}/${email}`);
  }

}

