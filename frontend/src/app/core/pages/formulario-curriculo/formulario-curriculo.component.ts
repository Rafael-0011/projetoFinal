import { Component, OnInit } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { AllServiceService } from '../../../infra/service/all-service.service';
import { CadastroCurriculoModel } from '../../../module/model/cadastro-curriculo-model';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EscolaridadeEnum } from '../../../module/Enumerate/escolaridade-enum';
import { NivelEnum } from '../../../module/Enumerate/nivel-enum';
import { CompetenciaEnum } from '../../../module/Enumerate/competencia-enum';
import { Router } from '@angular/router';
import { getEmailFromToken } from '../../../infra/auth/jwt';

@Component({
  selector: 'app-formulario-curriculo',
  imports: [BaseModule, PrimeNgModule],
  templateUrl: './formulario-curriculo.component.html',
  styleUrl: './formulario-curriculo.component.css',
})
export class FormularioCurriculoComponent implements OnInit {
  curriculoForm = new CadastroCurriculoModel();
  profileForm: any;

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

  constructor(
    private buildForm: FormBuilder,
    private service: AllServiceService,
    private route: Router
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
    });
  }

  ngOnInit(): void {
    this.addEmailNoInput();
  }

  get itemsSnacks() {
    return (this.profileForm.get('competencia') as FormArray)
      .controls as FormGroup[];
  }

  removeSnack(index: number) {
    (this.profileForm.get('competencia') as FormArray).removeAt(index);
  }

  addSnacks() {
    const newa = this.buildForm.group({
      competenciaEnum: [''],
      nivelEnum: [''],
    });
    return (this.profileForm.get('competencia') as FormArray).push(newa);
  }

  cadastraCurriculo(): void {
    this.service.cadastroCurriculo(this.profileForm.value).subscribe({
      next: (response) => {
        console.log(this.profileForm.value);
        this.route.navigate(['/homeUser']);
        console.log(response);
      },
      error: (err) => {
        console.log(err);
        this.route.navigate(['homeUser']);
      },
    });
  }

  addEmailNoInput() {
    const dadoEmail = getEmailFromToken();
    this.profileForm.patchValue({
      email: dadoEmail,
    });
  }
}
