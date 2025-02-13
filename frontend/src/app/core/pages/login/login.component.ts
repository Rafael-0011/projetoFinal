import { Component, inject } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AllServiceService } from '../../../infra/service/all-service.service';
import { LoginModel } from '../../../module/model/login-model';
import { StorageService } from '../../../infra/auth/storage.service';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { getAuthToken, refreshToken } from '../../../infra/auth/jwt';

@Component({
  selector: 'app-login',
  imports: [BaseModule, PrimeNgModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private service: AllServiceService,
    private storage: StorageService,
    private router: Router,
    private builderForm: FormBuilder
  ) {
    this.loginForm = this.builderForm.group({
      email: [''],
      password: [''],
    });
  }

  loginUser(): void {
    refreshToken();
    this.service.login(this.loginForm.value).subscribe({
      next: (response) => {
        this.storage.set('token', response.token);
        const email = this.loginForm.get('email')?.value;
        const authTokenRoleAdmin = getAuthToken();

        if (authTokenRoleAdmin) {
          this.router.navigate(['/homeAdmin']);
          return;
        }

        if (email) {
          this.service.obterCurriculoPorEmail(email!).subscribe({
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
