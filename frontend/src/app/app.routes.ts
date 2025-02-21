import { Routes } from '@angular/router';
import { LoginComponent } from './core/pages/login/login.component';
import { FormularioCurriculoComponent } from './core/pages/formulario-curriculo/formulario-curriculo.component';
import { HomeAdminComponent } from './core/pages/home-admin/home-admin.component';
import { CadastroUserComponent } from './core/pages/cadastro-user/cadastro-user.component';
import { HomeComponent } from './core/pages/home/home.component';
import { authGuard, authGuardAdmin } from './infra/guard/auth.guard';

export const routes: Routes = [
  {
    path: 'curriculo',
    component: FormularioCurriculoComponent,
    canActivate: [authGuard],
  },

  { path: '', component: LoginComponent },

  {
    path: 'homeAdmin',
    component: HomeAdminComponent,
    canActivate: [authGuardAdmin],
  },

  {
    path: 'cadastro',
    component: CadastroUserComponent,
    canActivate: [authGuard],
  },
  
  { path: 'homeUser', component: HomeComponent, canActivate: [authGuard] },
];
