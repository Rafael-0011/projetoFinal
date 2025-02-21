import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { BaseModule } from './shared/base/base.module';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, BaseModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'frontend';
}
