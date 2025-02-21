export const itensNiveis: NivelEnum[] = [];

export class NivelEnum {
  static BASICO = new NivelEnum('Básico', 'BASICO');
  static INTERMEDIARIO = new NivelEnum('Intermediário', 'INTERMEDIARIO');
  static AVANCADO = new NivelEnum('Avançado', 'AVANCADO');

  label: string;
  value: string;

  private constructor(descricao: string, value: string) {
    this.label = descricao;
    this.value = value;
    itensNiveis.push(this);
  }
}
