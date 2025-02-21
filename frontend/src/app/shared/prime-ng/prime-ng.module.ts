import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { PanelModule } from 'primeng/panel';
import { SplitButtonModule } from 'primeng/splitbutton';
import { StyleClassModule } from 'primeng/styleclass';
import { ToolbarModule } from 'primeng/toolbar';
import { CarouselModule } from 'primeng/carousel';
import { ChartModule } from 'primeng/chart';
import { IftaLabelModule } from 'primeng/iftalabel';
import { FloatLabelModule } from 'primeng/floatlabel';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { DatePickerModule } from 'primeng/datepicker';

@NgModule({
  declarations: [],
  imports: [
    ButtonModule,
    IconFieldModule,
    InputIconModule,
    SplitButtonModule,
    InputTextModule,
    StyleClassModule,
    CardModule,
    PanelModule,
    ToolbarModule,
    CarouselModule,
    ChartModule,
    IftaLabelModule,
    FloatLabelModule,
    SelectModule,
    TableModule,
    DialogModule,
    DatePickerModule
  ],

  exports: [
    ButtonModule,
    IconFieldModule,
    InputIconModule,
    SplitButtonModule,
    InputTextModule,
    StyleClassModule,
    CardModule,
    PanelModule,
    ToolbarModule,
    CarouselModule,
    ChartModule,
    IftaLabelModule,
    FloatLabelModule,
    SelectModule,
    TableModule,
    DialogModule,
    DatePickerModule
  ],
})
export class PrimeNgModule {}
