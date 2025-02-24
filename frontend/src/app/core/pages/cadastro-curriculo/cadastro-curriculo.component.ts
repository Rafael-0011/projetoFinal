import { Component } from '@angular/core';
import { FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TokenJwt } from '../../../infra/auth/jwt';
import { CurriculoService } from '../../../infra/service/curriculo.service';
import {
  niveisEnum,
  competenciasEnum,
  escolaridadeEnum,
} from '../../../shared/dadoEnum/dados-enum';
import { InputComponent } from '../../../shared/component/input/input.component';
import { BaseModule } from '../../../shared/base/base.module';
import { CustomValidationMessageComponent } from '../../../shared/component/custom-validation-message/custom-validation-message.component';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';

@Component({
  selector: 'app-cadastro-curriculo',
  imports: [
    BaseModule,
    PrimeNgModule,
    CustomValidationMessageComponent,
    InputComponent,
  ],
  templateUrl: './cadastro-curriculo.component.html',
  styleUrl: './cadastro-curriculo.component.css',
})
export class CadastroCurriculoComponent {
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
      name: ['', [Validators.required, Validators.minLength(3)]],
      cpf: ['', Validators.required],
      nascimento: ['', Validators.required],
      email: ['', Validators.required],
      telefone: ['', Validators.required],
      escolaridadeEnum: ['', Validators.required],
      funcao: ['', Validators.required],
      competencia: this.buildForm.array([]),
      user: [''],
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
    const id = this.tokenJwt.getIdFromToken();
    if (!id) {
      alert('Erro: Usuário não autenticado.');
      this.route.navigate(['/login']);
      return;
    }

    const curriculoData = { ...this.profileForm.value, user: id };

    this.curriculoService.cadastroCurriculo(curriculoData).subscribe({
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
