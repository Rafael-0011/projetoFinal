import { Component } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { AllServiceService } from '../../../infra/service/all-service.service';
import { CadastroCurriculoModel } from '../../../module/model/cadastro-curriculo-model';
import { StatusEnum } from '../../../module/Enumerate/status-enum';
import { FormGroup, FormArray, FormBuilder } from '@angular/forms';
import { NivelEnum } from '../../../module/Enumerate/nivel-enum';
import { CompetenciaEnum } from '../../../module/Enumerate/competencia-enum';
import { EscolaridadeEnum } from '../../../module/Enumerate/escolaridade-enum';

@Component({
  selector: 'app-home-admin',
  imports: [BaseModule, PrimeNgModule],
  templateUrl: './home-admin.component.html',
  styleUrl: './home-admin.component.css',
})
export class HomeAdminComponent {
  obterEmail!: any;
  selectedStatus: any;
  visible!: boolean;
  selectedIndex!: number;
  profileForm!: any;

  listUser: CadastroCurriculoModel[] = [];
  customers: any[] = [];

  listStatus = Object.keys(StatusEnum)
    .filter((key) => isNaN(Number(key)))
    .map((key) => ({
      label: key,
      value: key,
    }));

  labels: string[] = [];
  datasets: { label: string; data: number[] }[] = [];

  labels2: string[] = [];
  datasets2: { label: string[]; data: number[] }[] = [];

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
  ngOnInit() {
    this.getDadosGraficoStatus();
    this.getDadosGraficoEscolaridade();
    this.getListUsuarios();

  }

  getDadosGraficoEscolaridade(): void {
    this.service.obterGraficoEscolares().subscribe((data) => {
      this.labels = [];
      this.datasets = [{ label: 'Escolaridade', data: [] }];

      data.dadosGrafico.forEach((i) => {
        this.labels.push(i.escolaridade);
        this.datasets[0].data.push(i.quantidade);
      });
    });
  }

  getDadosGraficoStatus(): void {
    this.service.obterGraficoStatus().subscribe((data) => {
      this.labels2 = [];
      this.datasets2 = [{ label: [], data: [] }];

      data.dadosGrafico.forEach((i) => {
        this.labels2.push(i.status);
        this.datasets2[0].data.push(i.quantidade);
        this.datasets2[0].label.push(i.status);
      });
    });
  }

  getListUsuarios(): void {
    this.service.obterPaginacao().subscribe((data) => {
      this.listUser = data.content;

      this.customers = data.content.map((item: any) => ({
        name: item.name,
        email: item.email,
        funcao: item.funcao,
        status: item.statusEnum,
      }));
    });
  }

  alteraUse(): void {
    if (this.selectedIndex >= 0 && this.selectedIndex < this.listUser.length) {
      const user = this.listUser[this.selectedIndex];
      if (user) {
        user.statusEnum = this.selectedStatus || StatusEnum.EMESPERA;
        this.service.alteraStatusCurriculo(user.id, user).subscribe(() => {
          this.getDadosGraficoStatus();
          this.getDadosGraficoEscolaridade();
          this.getListUsuarios();
          this.visible = false;
        });
      } else {
        console.error('User is undefined at index:', this.selectedIndex);
      }
    } else {
      console.error('Invalid index:', this.selectedIndex);
    }
  }

  openDialog(index: number): void {
    if (index >= 0 && index < this.listUser.length) {
      this.selectedIndex = index;
      const email = this.listUser[index].email;
      this.carregarDadosUsuario(email);
      this.visible = true;
    } else {
      console.error('Invalid index:', index);
    }
  }

  carregarDadosUsuario(email: string): void {
    if (!email) {
      console.error('Email não encontrado');
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

      },
      error: (err) => console.error('Erro ao carregar usuário:', err),
    });
  }

  get itemsSnacks() {
    return (this.profileForm.get('competencia') as FormArray)
      .controls as FormGroup[];
  }

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
}
