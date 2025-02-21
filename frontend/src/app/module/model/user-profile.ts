export class UserProfile {
  name: string;
  cpf: string;
  nascimento: string;
  email: string;
  telefone: string;
  escolaridadeEnum: string;
  funcao: string;
  statusEnum: string;
  competencia: string[];

  constructor(data?: Partial<UserProfile>) {
    this.name = data?.name || '';
    this.cpf = data?.cpf || '';
    this.nascimento = data?.nascimento || '';
    this.email = data?.email || '';
    this.telefone = data?.telefone || '';
    this.escolaridadeEnum = data?.escolaridadeEnum || '';
    this.funcao = data?.funcao || '';
    this.statusEnum = data?.statusEnum || '';
    this.competencia = data?.competencia || [];
  }
}
