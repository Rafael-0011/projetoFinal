export interface GetEscolaridadeDto {
    escolaridade: string;
    quantidade: number;
  }
  
  export interface GetStatus {
    status: string;
    quantidade: number;
  }
  
  export interface StatusResponse {
    dadosGrafico: GetStatus[];
  }

  export interface EscolaridadeResponse {
    dadosGrafico: GetEscolaridadeDto[];
  }
  