export const itensEscolaridade: EscolaridadeEnum[] = [];

export class EscolaridadeEnum {
  static ANALFABETO = new EscolaridadeEnum('Analfabeto', 'ANALFABETO');
  static FUNDAMENTAL_COMPLETO = new EscolaridadeEnum('Fundamental Completo', 'FUNDAMENTAL_COMPLETO');
  static MEDIO_INCOMPLETO = new EscolaridadeEnum('Médio Incompleto', 'MEDIO_INCOMPLETO');
  static MEDIO_COMPLETO = new EscolaridadeEnum('Médio Completo', 'MEDIO_COMPLETO');
  static SUPERIOR_INCOMPLETO = new EscolaridadeEnum('Superior Incompleto', 'SUPERIOR_INCOMPLETO');
  static SUPERIOR_COMPLETO = new EscolaridadeEnum('Superior Completo', 'SUPERIOR_COMPLETO');
  static MESTRADO = new EscolaridadeEnum('Mestrado', 'MESTRADO');
  static DOUTORADO = new EscolaridadeEnum('Doutorado', 'DOUTORADO');
  static IGNORADO = new EscolaridadeEnum('Ignorado', 'IGNORADO');

  label: string;
  value: string;

  private constructor(label: string, value: string) {
    this.label = label;
    this.value = value;
    itensEscolaridade.push(this);
  }
}
