export interface JwtPayload {
    iss: string;
    sub: string;
    exp: number;
    iat: number;
    authorities: string;
  }
  