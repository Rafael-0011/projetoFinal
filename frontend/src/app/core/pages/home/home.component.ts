import { Component, OnInit } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { FormBuilder, FormArray, FormGroup } from '@angular/forms';
import { CurriculoService } from '../../../infra/service/curriculo.service';
import {
  niveisEnum,
  competenciasEnum,
  escolaridadeEnum,
  statusEnum,
} from '../../../shared/dadoEnum/dados-enum';
import { InputComponent } from '../../../shared/component/input/input.component';
import { TokenJwt } from '../../../infra/auth/jwt';

@Component({
  selector: 'app-home',
  imports: [BaseModule, PrimeNgModule, InputComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  profileForm: FormGroup;

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
    private tokenJwt: TokenJwt
  ) {
    this.profileForm = this.buildForm.group({
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

  isEdita:any;
  toggleFormState(altera:boolean): void {
    this.isEdita = altera;
    if (this.isEdita  === true) {
      this.profileForm.enable();
      const data = this.profileForm.get('statusEnum') as FormGroup;
      data?.disable();
    } else {
      this.profileForm.disable();
      const data = this.profileForm.get('statusEnum') as FormGroup;
      data?.disable();
    }
  }

  carregarDadosUsuario(): void {
    const email = this.tokenJwt.getEmailFromToken();
    if (!email) {
      console.error('Email não encontrado no token');
      return;
    }

    this.curriculoService.obterCurriculoPorEmail(email).subscribe({
      next: (response) => {
        const competenciasFormGroups: FormGroup[] = response.competencia.map(
          (item: any) =>
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
