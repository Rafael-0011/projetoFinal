import { Component, OnInit } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { FormBuilder, FormArray, FormGroup, Validators } from '@angular/forms';
import { CurriculoService } from '../../../infra/service/curriculo.service';
import {
  niveisEnum,
  competenciasEnum,
  escolaridadeEnum,
  statusEnum,
} from '../../../shared/dadoEnum/dados-enum';
import { InputComponent } from '../../../shared/component/input/input.component';
import { TokenJwt } from '../../../infra/auth/jwt';
import { UserService } from '../../../infra/service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [BaseModule, PrimeNgModule, InputComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  profileForm: FormGroup;
  isEdita: boolean = false;

  niveis = niveisEnum();
  competencias = competenciasEnum();
  escolaridade = escolaridadeEnum();
  status = statusEnum();

  get itemsSnacks() {
    return (this.profileForm.get('competencia') as FormArray)
      .controls as FormGroup[];
  }

  constructor(
    private buildForm: FormBuilder,
    private curriculoService: CurriculoService,
    private tokenJwt: TokenJwt,
    private userService: UserService,
    private route: Router
  ) {
    this.profileForm = this.buildForm.group({
      id: [''],
      name: [''],
      cpf: [''],
      nascimento: [''],
      email: [''],
      telefone: [''],
      escolaridadeEnum: [''],
      funcao: [''],
      statusEnum: [''],
      competencia: this.buildForm.array([]),
    });
  }
  ngOnInit(): void {
    this.carregarDadosUsuario();
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

  toggleFormState(altera: boolean): void {
    this.isEdita = altera;
    if (this.isEdita === true) {
      this.profileForm.enable();
      const data = this.profileForm.get('statusEnum') as FormGroup;
      data?.disable();
    } else {
      this.profileForm.disable();
      const data = this.profileForm.get('statusEnum') as FormGroup;
      data?.disable();
    }
  }

  alteraDados() {
    this.toggleFormState(true);
  }

  comfirmar() {
    this.toggleFormState(false);
    this.alteraCurriculo();
  }

  alteraCurriculo(): void {
    const id = this.tokenJwt.getIdFromToken();
    if (this.profileForm.invalid) {
      alert('Preencha todos os campos obrigatórios.');
      return;
    }
    if (!id) {
      alert('Erro: Usuário não autenticado.');
      this.route.navigate(['/login']);
      return;
    }
    const curriculoData = { ...this.profileForm.value };
    this.curriculoService.alterarCurriculo(curriculoData).subscribe({
      next: (response) => {
        this.carregarDadosUsuario();
        alert('Currículo atualizado com sucesso!');
      },
      error: (err) => {
        const errorMessage =
          err?.error?.message ||
          'Erro ao atualizar currículo. Tente novamente mais tarde.';
        alert(errorMessage);
      },
    });
  }

  carregarDadosUsuario(): void {
    const id = this.tokenJwt.getIdFromToken();
    if (!id) {
      console.error('Email não encontrado no token');
      return;
    }

    this.userService.obterCurriculoPeloUserId(id).subscribe({
      next: (response) => {
        const competenciasFormGroups: FormGroup[] = response.competencia.map(
          (item: any) =>
            this.buildForm.group({
              competenciaEnum: [item.competenciaEnum],
              nivelEnum: [item.nivelEnum],
            })
        );

        this.profileForm.patchValue({
          id: response.id,
          name: response.name,
          cpf: response.cpf,
          nascimento: response.nascimento ? new Date(response.nascimento + 'T12:00:00') : null,
          email: response.email,
          telefone: response.telefone,
          escolaridadeEnum: response.escolaridadeEnum,
          funcao: response.funcao,
          statusEnum: response.statusEnum || 'AGUARDANDO',
          competencia: response.competencia || [],
        });

        const competenciaArray = this.profileForm.get(
          'competencia'
        ) as FormArray;
        competenciaArray.clear();
        competenciasFormGroups.forEach((control) =>
          competenciaArray.push(control)
        );

        this.toggleFormState(this.isEdita);
      },

      error: (err) => console.error('Erro ao carregar usuário:', err),
    });
  }


}
