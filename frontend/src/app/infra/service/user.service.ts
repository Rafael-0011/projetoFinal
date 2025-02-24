import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';
import { CadastroCurriculoModel } from '../../module/model/cadastro-curriculo-model';
import { CadastroUserModel } from '../../module/model/cadastro-user-model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  cadastroUser(user: CadastroUserModel): Observable<CadastroUserModel> {
    const url = `${environment.URL_USER + environment.CADASTRO}`
    return this.http.post<CadastroUserModel>(`${url}`, user);
  }

  obterCurriculoPeloUserId(id: number): Observable<any> {
    const url = `${environment.URL_USER}/${id}`;
    return this.http.get<any>(`${url}`);
  }
}
