import { CompetenciaEnum } from "../Enumerate/competencia-enum";
import { EscolaridadeEnum } from "../Enumerate/escolaridade-enum";
import { NivelEnum } from "../Enumerate/nivel-enum";
import { StatusEnum } from "../Enumerate/status-enum";
import { CompotenciaMoral } from "./competencia-moral";

export class CadastroCurriculoModel {
  id!:number
  name!: string;
  cpf!: string;
  nascimento!: string;
  email!: string;
  telefone!: string;
  escolaridadeEnum!: EscolaridadeEnum;
  funcao!: string;
  competencia!: CompotenciaMoral;
  statusEnum!:StatusEnum
}
