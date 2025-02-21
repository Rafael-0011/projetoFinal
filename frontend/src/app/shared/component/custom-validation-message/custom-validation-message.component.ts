import { Component, inject, Input } from '@angular/core';
import { BaseModule } from '../../base/base.module';
import { ControlContainer, FormControl, FormGroup } from '@angular/forms';
import { PrimeNgModule } from '../../prime-ng/prime-ng.module';

@Component({
  selector: 'app-custom-validation-message',
  imports: [PrimeNgModule,BaseModule],
  templateUrl: './custom-validation-message.component.html',
  styleUrl: './custom-validation-message.component.css'
})
export class CustomValidationMessageComponent {

  @Input({ required: true })
  public controlName!: string;

  @Input({required: false})
  public minLength!: number;

  @Input({required: false})
  public maxLength!: number;

  private _controlContainer = inject(ControlContainer);

  get form(): FormGroup {
    return this._controlContainer.control as FormGroup;
  }

  get control(): FormControl {
    return this.form.get(this.controlName) as FormControl;
  }
}
