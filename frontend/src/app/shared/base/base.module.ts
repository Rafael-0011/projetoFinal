import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimeNgModule } from '../prime-ng/prime-ng.module';
import { ButtonComponent } from '../component/button/button.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChartBarComponent } from '../component/chart-bar/chart-bar.component';
import { ChartPieComponent } from '../component/chart-pie/chart-pie.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [ButtonComponent, ChartPieComponent, ChartBarComponent],
  imports: [
    PrimeNgModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
  ],

  exports: [
    ButtonComponent,
    ChartPieComponent,
    ChartBarComponent,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
  ],
})
export class BaseModule {}
