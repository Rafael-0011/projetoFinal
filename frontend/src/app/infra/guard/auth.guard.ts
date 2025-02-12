import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { JwtPayload } from '../../module/inteface/jwtPayload';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');
  if (!token) {
    router.navigateByUrl('');
    return false;
  }
  return true;
};

export const authGuardAdmin: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');

  if (!token) {
    router.navigateByUrl('');
    return false;
  }

  try {
    const decoded: JwtPayload = jwtDecode<JwtPayload>(token);

    const exp = decoded.exp * 1000;

    if (decoded.authorities.includes('ROLE_ADMIN') && exp > Date.now()) {
      return true;
    }
  } catch (error) {
    console.error('Erro ao decodificar JWT:', error);
  }

  router.navigateByUrl('');
  return false;
};
