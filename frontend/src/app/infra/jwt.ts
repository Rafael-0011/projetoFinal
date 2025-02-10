import {jwtDecode} from 'jwt-decode'

export function getEmailFromToken(): string | null {
  const token = localStorage.getItem('token');
  if (!token) return null;

  try {
    if (!token) return null;
    const decoded: any = jwtDecode(token);

    return decoded.sub;
  } catch (error) {
    console.error('Erro ao decodificar o token:', error);
    return null;
  }
}