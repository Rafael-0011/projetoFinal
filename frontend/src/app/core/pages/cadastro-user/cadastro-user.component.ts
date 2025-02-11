import { Component } from '@angular/core';
import { BaseModule } from '../../../shared/base/base.module';
import { PrimeNgModule } from '../../../shared/prime-ng/prime-ng.module';
import { FormBuilder } from '@angular/forms';
import { AllServiceService } from '../../../infra/service/all-service.service';
import { routes } from '../../../app.routes';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastro-user',
  imports: [BaseModule, PrimeNgModule],
  templateUrl: './cadastro-user.component.html',
  styleUrl: './cadastro-user.component.css',
})
export class CadastroUserComponent {
  profileForm: any;

  constructor(
    private buildForm: FormBuilder,
    private service: AllServiceService,
    private route: Router
  ) {
    this.profileForm = this.buildForm.group({
      email: [''],
      password: [''],
    });
  }

  cadastraUser(): void {
    this.service.cadastroUser(this.profileForm.value).subscribe({
      next: (response) => {
        console.log(response);
        this.route.navigate(['/']);
      },
      error: (err) => {
        console.log(err, 'ocorre erro ao cadastra');
      },
    });
  }
}
