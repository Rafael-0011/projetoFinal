import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrl: './button.component.css',
  standalone: false,
})
export class ButtonComponent {
  @Input() function!:()=>void;

  @Input() text!:string;

  
  onClick(): void {
    if (this.function) {
      this.function();  
    }
  }
}
