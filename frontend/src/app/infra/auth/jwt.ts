import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class TokenJwt {
  private helperService = new JwtHelperService();
  private tokenKey = 'token';

  private getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getTokenDecoded() {
    const token = this.getToken();
    if (!token) return null;

    try {
      return this.helperService.decodeToken(token);
    } catch (error) {
      console.error('Erro ao decodificar o token:', error);
      return null;
    }
  }

  expireToken(): boolean {
    const token = this.getToken();
    if (!token) return false;

    const tokenExpirado = this.helperService.isTokenExpired(token);
    if (tokenExpirado) {
      localStorage.removeItem(this.tokenKey);
      return true;
    }

    return false;
  }

  getEmailFromToken(): string | false {
    return this.getTokenDecoded()?.sub || false;
  }

  hasRole(role: string): boolean {
    return this.getTokenDecoded()?.authorities?.includes(role) || false;
  }

  getAuthAdminToken(): boolean {
    return this.hasRole('ROLE_ADMIN');
  }
}
