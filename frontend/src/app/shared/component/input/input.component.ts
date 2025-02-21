import { Component, inject, Input } from '@angular/core';
import { BaseModule } from '../../base/base.module';
import { PrimeNgModule } from '../../prime-ng/prime-ng.module';
import { CustomValidationMessageComponent } from '../custom-validation-message/custom-validation-message.component';
import { FormGroup, FormControl, ControlContainer, FormGroupDirective } from '@angular/forms';

@Component({
  selector: 'app-input',
  imports: [BaseModule, PrimeNgModule, CustomValidationMessageComponent],
  templateUrl: './input.component.html',
  styleUrl: './input.component.css',
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }]

})
export class InputComponent {
  @Input() controlName!: string;
  @Input() label!: string;

  private _controlContainer = inject(ControlContainer);

  get form(): FormGroup {
    return this._controlContainer.control as FormGroup;
  }

  get control(): FormControl {
    return this.form.get(this.controlName) as FormControl;
  }
}
