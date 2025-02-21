import { Component, OnInit } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {
  competenciasEnum,
  escolaridadeEnum,
  niveisEnum,
} from '../../../shared/dadoEnum/dados-enum';
import { CustomValidationMessageComponent } from '../../../shared/component/custom-validation-message/custom-validation-message.component';
import { InputComponent } from '../../../shared/component/input/input.component';
import { CurriculoService } from '../../../infra/service/curriculo.service';
import { TokenJwt } from '../../../infra/auth/jwt';

@Component({
  selector: 'app-formulario-curriculo',
  imports: [
    BaseModule,
    PrimeNgModule,
    CustomValidationMessageComponent,
    InputComponent,
  ],
  templateUrl: './formulario-curriculo.component.html',
  styleUrl: './formulario-curriculo.component.css',
})
export class FormularioCurriculoComponent implements OnInit {
  profileForm: FormGroup;
  niveis = niveisEnum();
  competencias = competenciasEnum();
  escolaridade = escolaridadeEnum();

  get itemsSnacks() {
    return (this.profileForm.get('competencia') as FormArray)
      .controls as FormGroup[];
  }

  constructor(
    private buildForm: FormBuilder,
    private route: Router,
    private curriculoService: CurriculoService,
    private tokenJwt: TokenJwt
  ) {
    this.profileForm = this.buildForm.group({
      name: ['', Validators.required, Validators.minLength(3)],
      cpf: ['', Validators.required],
      nascimento: ['', Validators.required],
      email: ['', Validators.required],
      telefone: ['', Validators.required],
      escolaridadeEnum: ['', Validators.required],
      funcao: ['', Validators.required],
      competencia: this.buildForm.array([]),
    });
  }

  ngOnInit(): void {
    this.addEmailNoInput();
  }

  cadastraCurriculo(): void {
    if (this.profileForm.invalid) {
      alert('Preencha todos os campos obrigatórios.');
      return;
    }

    this.curriculoService.cadastroCurriculo(this.profileForm.value).subscribe({
      next: (response) => {
        alert('Curriculo Castrado');
        this.route.navigate(['/homeUser']);
      },
      error: (err) => {
        console.log(err);
        console.log(this.profileForm.value);

        alert('dados faltando');
        this.route.navigate(['/curriculo']);
      },
    });
  }

  addEmailNoInput() {
    const dadoEmail = this.tokenJwt.getEmailFromToken();
    this.profileForm.patchValue({
      email: dadoEmail,
    });
  }

  removeSnack(index: number) {
    (this.profileForm.get('competencia') as FormArray).removeAt(index);
  }

  addSnacks() {
    const newa = this.buildForm.group({
      competenciaEnum: ['', Validators.required],
      nivelEnum: ['', Validators.required],
    });
    return (this.profileForm.get('competencia') as FormArray).push(newa);
  }
}
