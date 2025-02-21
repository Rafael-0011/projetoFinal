export const itensStatus: StatusEnum[] = [];

export class StatusEnum {
  static APROVADO = new StatusEnum('Aprovado', 'APROVADO');
  static REPROVADO = new StatusEnum('Reprovado', 'REPROVADO');
  static AGUARDANDO = new StatusEnum('Aguardando', 'AGUARDANDO');

  label: string;
  value: string;

  private constructor(label: string, value: string) {
    this.label = label;
    this.value = value;
    itensStatus.push(this);
  }
}
