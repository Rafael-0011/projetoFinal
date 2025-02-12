import { CompetenciaEnum } from '../../module/Enumerate/competencia-enum';
import { EscolaridadeEnum } from '../../module/Enumerate/escolaridade-enum';
import { NivelEnum } from '../../module/Enumerate/nivel-enum';
import { StatusEnum } from '../../module/Enumerate/status-enum';

export function niveisEnum() {
  const niveis = Object.keys(NivelEnum)
    .filter((key) => isNaN(Number(key)))
    .map((key) => ({
      label: key,
      value: key,
    }));

  return niveis;
}

export function escolaridadeEnum() {
  const escolaridade = Object.keys(EscolaridadeEnum)
    .filter((key) => isNaN(Number(key)))
    .map((key) => ({
      label: key,
      value: key,
    }));
  return escolaridade;
}

export function competenciasEnum() {
  const competencias = Object.keys(CompetenciaEnum)
    .filter((key) => isNaN(Number(key)))
    .map((key) => ({
      label: key,
      value: key,
    }));
  return competencias;
}

export function listStatusEnum() {
  const listStatus = Object.keys(StatusEnum)
    .filter((key) => isNaN(Number(key)))
    .map((key) => ({
      label: key,
      value: key,
    }));
  return listStatus;
}
