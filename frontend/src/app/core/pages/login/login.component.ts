import { Component } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from '../../../infra/auth/storage.service';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { InputComponent } from '../../../shared/component/input/input.component';
import { LoginService } from '../../../infra/service/login.service';
import { CurriculoService } from '../../../infra/service/curriculo.service';
import { TokenJwt } from '../../../infra/auth/jwt';
import { UserService } from '../../../infra/service/user.service';

@Component({
  selector: 'app-login',
  imports: [BaseModule, PrimeNgModule, RouterModule, InputComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private loginService: LoginService,
    private curriculoService: CurriculoService,
    private storage: StorageService,
    private router: Router,
    private builderForm: FormBuilder,
    private tokenJwt: TokenJwt,
    private userService: UserService
  ) {
    this.loginForm = this.builderForm.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  loginUser(): void {
    this.tokenJwt.expireToken();
    if (this.loginForm.invalid) {
      alert('dados faltando');
      return;
    }
    this.loginService.login(this.loginForm.value).subscribe({
      next: (response) => {
        this.storage.set('token', response.token);
        const authTokenRoleAdmin = this.tokenJwt.getAuthAdminToken();
        const id = this.tokenJwt.getIdFromToken();

        if (authTokenRoleAdmin) {
          this.router.navigate(['/homeAdmin']);
          return;
        }
        if (id) {
          this.userService.obterCurriculoPeloUserId(id).subscribe({
            next: () => {
              this.router.navigate(['/homeUser']);
            },
            error:()=>{
              this.router.navigate(['/cadastroCurriculo']);
            }
          });
        }
      },
      error: () => {
        alert('Sem cadastro');
      },
    });
  }
}
