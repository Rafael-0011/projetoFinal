import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.development';
import { LoginModel } from '../../module/model/login-model';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  login(login: LoginModel): Observable<LoginModel> {
    const url = `${environment.URL_BASE + environment.LOGIN}`;
    return this.http.post<LoginModel>(`${url}`, login);
  }
}
