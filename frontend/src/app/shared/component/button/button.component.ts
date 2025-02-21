import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrl: './button.component.css',
  standalone: false,
})
export class ButtonComponent {
  @Input() onClickFunction!:()=>void;

  @Input() text!:string;


  onClick(): void {
    if (this.onClickFunction) {
      this.onClickFunction();  
    }
  }
}
