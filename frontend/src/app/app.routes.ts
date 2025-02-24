import { Routes } from '@angular/router';
import { LoginComponent } from './core/pages/login/login.component';
import { HomeAdminComponent } from './core/pages/home-admin/home-admin.component';
import { CadastroUserComponent } from './core/pages/cadastro-user/cadastro-user.component';
import { HomeComponent } from './core/pages/home/home.component';
import { authGuard, authGuardAdmin } from './infra/guard/auth.guard';
import { CadastroCurriculoComponent } from './core/pages/cadastro-curriculo/cadastro-curriculo.component';

export const routes: Routes = [
  {
    path: 'cadastroCurriculo',
    component: CadastroCurriculoComponent,
    canActivate: [authGuard],
  },

  {
    path: '',
    component: LoginComponent,
  },

  {
    path: 'homeAdmin',
    component: HomeAdminComponent,
    canActivate: [authGuardAdmin],
  },

  {
    path: 'cadastroUser',
    component: CadastroUserComponent,
  },

  {
    path: 'homeUser',
    component: HomeComponent,
    canActivate: [authGuard],
  },
];
