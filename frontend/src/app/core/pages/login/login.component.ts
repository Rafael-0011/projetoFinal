import { Component, inject } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AllServiceService } from '../../../infra/service/all-service.service';
import { LoginModel } from '../../../module/model/login-model';
import { StorageService } from '../../../infra/auth/storage.service';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [BaseModule, PrimeNgModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  loginForm = new LoginModel();

  constructor(
    private service: AllServiceService,
    private storage: StorageService,
    private router: Router
  ) {}

  loginUser(): void {
    this.service.login(this.loginForm).subscribe({
      next: (response) => {
        this.storage.set('token', response.token);
        this.router.navigate(['/curriculo']);
      },
      error: () => {
        console.log('Erro ao acessa');
      },
    });
  }
}
