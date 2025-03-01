export class UserProfile {
  id: number;
  name: string;
  cpf: string;
  nascimento: Date | null;
  email: string;
  telefone: string;
  escolaridadeEnum: string;
  funcao: string;
  statusEnum: string;
  competencia: string[];

  constructor(data?: Partial<UserProfile>) {
    this.id = data?.id || 0;
    this.name = data?.name || '';
    this.cpf = data?.cpf || '';
    this.nascimento = data?.nascimento ? new Date(data.nascimento + 'T12:00:00') : null;
    this.email = data?.email || '';
    this.telefone = data?.telefone || '';
    this.escolaridadeEnum = data?.escolaridadeEnum || '';
    this.funcao = data?.funcao || '';
    this.statusEnum = data?.statusEnum || '';
    this.competencia = data?.competencia || [];
  }
}
