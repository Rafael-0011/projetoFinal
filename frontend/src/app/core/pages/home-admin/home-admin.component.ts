import { Component } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { StatusEnum } from '../../../module/Enumerate/status-enum';
import { FormGroup, FormArray, FormBuilder } from '@angular/forms';
import { UserProfile } from '../../../module/model/user-profile';
import { CurriculoService } from '../../../infra/service/curriculo.service';
import { GraficoService } from '../../../infra/service/grafico.service';
import {
  escolaridadeEnum,
  niveisEnum,
  statusEnum,
} from '../../../shared/dadoEnum/dados-enum';
import { AlteraStatusModel } from '../../../module/model/altera-status-model';
import { CompotenciaModal } from '../../../module/model/competencia-moral';
import { competenciasEnum } from '../../../shared/dadoEnum/dados-enum';
import { UserTableComponent } from "../../../shared/component/user-table/user-table.component";

@Component({
  selector: 'app-home-admin',
  imports: [BaseModule, PrimeNgModule, UserTableComponent],
  templateUrl: './home-admin.component.html',
  styleUrl: './home-admin.component.css',
})
export class HomeAdminComponent {
  visible!: boolean;
  selectedIndex!: number;
  profileForm: FormGroup;

  listUser: AlteraStatusModel[] = [];
  customers: any[] = [];

  status = statusEnum();

  graficBarLabels: string[] = [];
  graficBarDatasets: { label: string; data: number[] }[] = [];

  graficPieLabels: string[] = [];
  graficPieDatasets: { label: string; data: number[] }[] = [];

  get itemsSnacks() {
    return (this.profileForm.get('competencia') as FormArray)
      .controls as FormGroup[];
  }

  constructor(
    private buildForm: FormBuilder,
    private graficoService: GraficoService,
    private curriculoService: CurriculoService,
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
      competencia: this.buildForm.array([]),
      statusEnum: [''],
    });
  }
  ngOnInit() {
    this.getDadosGraficoStatus();
    this.getDadosGraficoEscolaridade();
    this.getListUsuariosParaTabela();
  }

  getDadosGraficoEscolaridade(): void {
    this.graficoService.obterGraficoEscolares().subscribe((data) => {
      this.graficBarLabels = [];
      this.graficBarDatasets = [{ label: 'Escolaridade', data: [] }];

      data.dadoGrafico.forEach((i) => {
        const labelEncontrado = escolaridadeEnum().find(
          (nivel) => nivel.value === i.dado
        )?.label;

        this.graficBarLabels.push(labelEncontrado || 'Desconhecido');
        this.graficBarDatasets[0].data.push(i.quantidade);
      });
    });
  }

  getDadosGraficoStatus(): void {
    this.graficoService.obterGraficoStatus().subscribe((data) => {
      this.graficPieLabels = [];
      this.graficPieDatasets = [{ label: 'Quantidade', data: [] }];

      data.dadoGrafico.forEach((i) => {
        this.graficPieLabels.push(i.dado);
        this.graficPieDatasets[0].data.push(i.quantidade);
      });
    });
  }

  getListUsuariosParaTabela(): void {
    this.curriculoService.obterListaCurriculo().subscribe((data) => {
      this.listUser = data;

      this.customers = data.map((item: any) => ({
        id: item.id,
        name: item.name,
        email: item.email,
        funcao: item.funcao,
        status: item.statusEnum,
        user: item.user,
      }));
    });
  }

  openDialog(index: number): void {
    if (index >= 0 && index < this.listUser.length) {
      this.selectedIndex = index;
      const id = this.listUser[index].id;
      this.carregarDadoCurriculoUsuario(id);
      this.visible = true;
    } else {
      console.error('Invalid index:', index);
    }
  }

  alteraStatusCurriculoUser(): void {
    if (this.selectedIndex >= 0 && this.selectedIndex < this.listUser.length) {
      const user = this.listUser[this.selectedIndex];

      if (user) {
        user.statusEnum =
          this.profileForm.get('statusEnum')?.value || StatusEnum.AGUARDANDO;
        this.curriculoService
          .alteraStatusCurriculo(user.id, user)
          .subscribe(() => {
            this.getDadosGraficoStatus();
            this.getListUsuariosParaTabela();
            this.visible = false;
          });

      } else {
        console.error('User is undefined at index:', this.selectedIndex);
      }
    } else {
      console.error('Invalid index:', this.selectedIndex);
    }
  }

  carregarDadoCurriculoUsuario(id: number): void {
    if (!id) {
      console.error('Id dos usuarios não encontrado');
      return;
    }
    this.curriculoService.obterCurriculoPeloCurriculoId(id).subscribe({
      next: (response) => {
        const competenciasFormGroups: FormGroup[] = response.competencia.map(
          (item: CompotenciaModal) =>
            this.buildForm.group({
              competenciaEnum: [
                competenciasEnum().find(
                  (competencia) =>
                    competencia.value === String(item.competenciaEnum)
                )?.label || null,
              ],
              nivelEnum: niveisEnum().find(
                (nivel) => nivel.value === String(item.nivelEnum)
              )?.label,
            })
        );

        this.profileForm.patchValue({
          ...new UserProfile(response),
          escolaridadeEnum: escolaridadeEnum().find(
            (i) => i.value === response.escolaridadeEnum
          )?.label,
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
}
