import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BaseModule } from '../../base/base.module';
import { PrimeNgModule } from '../../prime-ng/prime-ng.module';

@Component({
  selector: 'app-user-table',
  imports: [BaseModule,PrimeNgModule],
  templateUrl: './user-table.component.html',
  styleUrl: './user-table.component.css'
})
export class UserTableComponent {
  @Input() customers: any[] = [];
  @Output() alterUser = new EventEmitter<number>();

  openDialog(index: number): void {
    this.alterUser.emit(index);
  }
}
