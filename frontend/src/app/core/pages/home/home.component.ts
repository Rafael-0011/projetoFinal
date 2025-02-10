import { Component, OnInit } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { FormBuilder, FormArray, FormGroup } from '@angular/forms';
import { AllServiceService } from '../../../infra/all-service.service';
import { CompetenciaEnum } from '../../../module/Enumerate/competencia-enum';
import { EscolaridadeEnum } from '../../../module/Enumerate/escolaridade-enum';
import { NivelEnum } from '../../../module/Enumerate/nivel-enum';
import { CadastroCurriculoModel } from '../../../module/model/cadastro-curriculo-model';
import { getEmailFromToken } from '../../../infra/jwt';
import { StatusEnum } from '../../../module/Enumerate/status-enum';

@Component({
  selector: 'app-home',
  imports: [BaseModule, PrimeNgModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  curriculoForm = new CadastroCurriculoModel();
  profileForm: any;
  listUser: CadastroCurriculoModel[] = [];

  niveis = Object.keys(NivelEnum)
    .filter((key) => isNaN(Number(key)))
    .map((key) => ({
      label: key,
      value: key,
    }));

  competencias = Object.keys(CompetenciaEnum)
    .filter((key) => isNaN(Number(key)))
    .map((key) => ({
      label: key,
      value: key,
    }));

  escolaridade = Object.keys(EscolaridadeEnum)
    .filter((key) => isNaN(Number(key)))
    .map((key) => ({
      label: key,
      value: key,
    }));

  listStatus = Object.keys(StatusEnum)
    .filter((key) => isNaN(Number(key)))
    .map((key) => ({
      label: key,
      value: key,
    }));

  constructor(
    private buildForm: FormBuilder,
    private service: AllServiceService
  ) {
    this.profileForm = this.buildForm.group({
      name: [''],
      cpf: [''],
      nascimento: [''],
      email: [''],
      telefone: [''],
      escolaridadeEnum: [''],
      funcao: [''],
      competencia: this.buildForm.array([]),
      statusEnum: [''],
    });
  }
  ngOnInit(): void {
    this.carregarDadosUsuario();
  }

  get itemsSnacks() {
    return (this.profileForm.get('competencia') as FormArray)
      .controls as FormGroup[];
  }

  removeSnack(index: number) {
    (this.profileForm.get('competencia') as FormArray).removeAt(index);
  }

  /*
  addSnacks(){
    
    const newa = this.buildForm.group({
      competenciaEnum: [''],
      nivelEnum:['']
    })
    return (this.profileForm.get('competencia') as FormArray).push(newa)
  }
    */

  carregarDadosUsuario(): void {
    const email = getEmailFromToken();
    if (!email) {
      console.error('Email não encontrado no token');
      return;
    }
    interface Competencia {
      competenciaEnum: string;
      nivelEnum: string;
    }
    this.service.obterCurriculoPorEmail(email).subscribe({
      next: (response) => {
        const competenciasFormGroups: FormGroup[] = response.competencia.map(
          (item: Competencia) =>
            this.buildForm.group({
              competenciaEnum: [item.competenciaEnum],
              nivelEnum: [item.nivelEnum],
            })
        );
        this.profileForm.patchValue({
          name: response.name,
          cpf: response.cpf,
          nascimento: response.nascimento,
          email: response.email,
          telefone: response.telefone,
          escolaridadeEnum: response.escolaridadeEnum,
          funcao: response.funcao,
          statusEnum: response.statusEnum,
          competencia: response.competencia || [],
        });
        const competenciaArray = this.profileForm.get(
          'competencia'
        ) as FormArray;
        competenciaArray.clear();
        competenciasFormGroups.forEach((control) =>
          competenciaArray.push(control)
        );

        console.log(response);
      },
      error: (err) => console.error('Erro ao carregar usuário:', err),
    });
  }
}
