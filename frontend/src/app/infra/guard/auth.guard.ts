import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenJwt } from '../auth/jwt';

const validateToken = (tokenJwt: TokenJwt, router: Router): boolean => {
  const tokenExpire = tokenJwt.expireToken();
  const email = tokenJwt.getEmailFromToken();

  if (tokenExpire || !email) {
    router.navigateByUrl('');
    return false;
  }
  return true;
};

const hasRole = (tokenJwt: TokenJwt, role: string): boolean => {
  try {
    const token = tokenJwt.getTokenDecoded();
    return token?.authorities?.includes(role);
  } catch (error) {
    console.error('Erro ao decodificar JWT:', error);
    return false;
  }
};

export const authGuard: CanActivateFn = (route, state) => {
  const tokenJwt = inject(TokenJwt);
  const router = inject(Router);
  return validateToken(tokenJwt, router);
};

export const authGuardAdmin: CanActivateFn = (route, state) => {
  const tokenJwt = inject(TokenJwt);
  const router = inject(Router);

  if (!validateToken(tokenJwt, router)) {
    return false;
  }

  if (hasRole(tokenJwt, 'ROLE_ADMIN')) {
    return true;
  }

  router.navigateByUrl('');
  return false;
};
