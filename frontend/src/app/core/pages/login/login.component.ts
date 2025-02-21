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
    private tokenJwt: TokenJwt
  ) {
    this.loginForm = this.builderForm.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  loginUser(): void {
    this.tokenJwt.expireToken();
    this.loginService.login(this.loginForm.value).subscribe({
      next: (response) => {
        this.storage.set('token', response.token);
        const email = this.loginForm.get('email')?.value;
        const authTokenRoleAdmin = this.tokenJwt.getAuthAdminToken();

        if (authTokenRoleAdmin) {
          this.router.navigate(['/homeAdmin']);
          return;
        }

        if (email) {
          this.curriculoService.obterCurriculoPorEmail(email!).subscribe({
            next: () => {
              this.router.navigate(['/homeUser']);
            },
            error: () => {
              this.router.navigate(['/curriculo']);
            },
          });
        }
      },
      error: () => {
        alert('Sem cadastro');
      },
    });
  }
}
