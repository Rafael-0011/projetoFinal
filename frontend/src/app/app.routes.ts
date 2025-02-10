import { Routes } from '@angular/router';
import { LoginComponent } from './core/pages/login/login.component';
import { FormularioCurriculoComponent } from './core/pages/formulario-curriculo/formulario-curriculo.component';
import { HomeAdminComponent } from './core/pages/home-admin/home-admin.component';
import { CadastroUserComponent } from './core/pages/cadastro-user/cadastro-user.component';
import { HomeComponent } from './core/pages/home/home.component';

export const routes: Routes = [
  { path: 'curriculo', component: FormularioCurriculoComponent },
  { path: '', component: LoginComponent },
  { path: 'homeAdmin', component: HomeAdminComponent },
  { path: 'cadastro', component: CadastroUserComponent },
  { path: 'homeUser', component: HomeComponent },
];
