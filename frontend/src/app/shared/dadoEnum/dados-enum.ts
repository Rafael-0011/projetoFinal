import { itensCompetencias } from '../../module/Enumerate/competencia-enum';
import { itensEscolaridade } from '../../module/Enumerate/escolaridade-enum';
import { itensNiveis } from '../../module/Enumerate/nivel-enum';
import { itensStatus } from '../../module/Enumerate/status-enum';

export function niveisEnum() {
  return itensNiveis.map((item) => ({
    label: item.label,
    value: item.value,
  }));
}

export function escolaridadeEnum() {
  return itensEscolaridade.map((item) => ({
    label: item.label,
    value: item.value,
  }));
}

export function competenciasEnum() {
  return itensCompetencias.map((item) => ({
    label: item.label,
    value: item.value,
  }));
}

export function statusEnum() {
  return itensStatus.map((item) => ({
    label: item.label,
    value: item.value,
  }));
}
