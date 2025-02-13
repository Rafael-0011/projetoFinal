import { jwtDecode } from 'jwt-decode';
import { JwtPayload } from '../../module/inteface/jwtPayload';

export function getEmailFromToken(): string | null {
  const token = localStorage.getItem('token');
  if (!token) return null;

  try {
    if (!token) return null;
    const decoded: any = jwtDecode(token);

    return decoded.sub;
  } catch (error) {
    console.error('getEmailFromToken:', error);
    return null;
  }
}

export function getAuthToken() {
  const token = localStorage.getItem('token');
  if (!token) return null;

  try {
    const decoded: JwtPayload = jwtDecode<JwtPayload>(token);

    const authToken = decoded.authorities;

    if (authToken.includes('ROLE_ADMIN')) {
      return true;
    }
    return false;
  } catch (error) {
    console.error('getAuthToken:', error);
    return null;
  }
}

export function refreshToken() {
  const token = localStorage.getItem('token');
  if (!token) return null;
  try {
    const decoded: JwtPayload = jwtDecode<JwtPayload>(token);
    const authToken = decoded.exp;
    const expire = authToken * 1000;

    if (expire < Date.now()) {
      localStorage.removeItem('token');
      return true;
    }
    return false;
  } catch (error) {
    console.error('refreshToken:', error);
    return null;
  }
}
