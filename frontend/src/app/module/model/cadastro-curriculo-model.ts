import { CompetenciaEnum } from "../Enumerate/competencia-enum";
import { EscolaridadeEnum } from "../Enumerate/escolaridade-enum";
import { CompotenciaModal } from "./competencia-moral";

export class CadastroCurriculoModel {
  id!:number
  name!: string;
  cpf!: string;
  nascimento!: string;
  email!: string;
  telefone!: string;
  escolaridadeEnum!: EscolaridadeEnum;
  funcao!: string;
  competencia!: CompotenciaModal[];
}
