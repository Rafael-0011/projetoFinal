import { Component } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { InputComponent } from '../../../shared/component/input/input.component';
import { UserService } from '../../../infra/service/user.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-cadastro-user',
  imports: [BaseModule, PrimeNgModule, InputComponent],
  templateUrl: './cadastro-user.component.html',
  styleUrl: './cadastro-user.component.css',
})
export class CadastroUserComponent {
  profileForm: FormGroup;

  constructor(
    private buildForm: FormBuilder,
    private route: Router,
    private userService: UserService
  ) {
    this.profileForm = this.buildForm.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  cadastraUser(): void {
    this.userService.cadastroUser(this.profileForm.value).subscribe({
      next: (response) => {
        alert("Conta criada")
        this.route.navigate(['/']);
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 409) {
          alert('E-mail já cadastrado. Por favor, use outro e-mail.');
        } else {
          alert('Ocorreu um erro ao cadastrar. Tente novamente mais tarde.');
        }
      },
    });
  }
}
