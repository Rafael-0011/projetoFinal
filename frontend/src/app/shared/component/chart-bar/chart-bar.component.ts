import { Component, Input, SimpleChanges } from '@angular/core';
import { EscolaridadeEnum } from '../../../module/Enumerate/escolaridade-enum';
import { escolaridadeEnum } from '../../dadoEnum/dados-enum';

@Component({
  selector: 'app-chart-bar',
  templateUrl: './chart-bar.component.html',
  styleUrl: './chart-bar.component.css',
  standalone: false,
})
export class ChartBarComponent {
  data: any;

  @Input() labels: string[] = [];
  @Input() datasets: { label: string; data: number[] }[] = [];
  options: any;
  ngOnChanges(changes: SimpleChanges) {
    if (changes['labels'] || changes['datasets']) {
      this.updateChart();
    }
  }

  updateChart() {
    const colorMap: { [key: string]: string } = {
      ANALFABETO: '#8B0000',
      FUNDAMENTAL_COMPLETO: '#FF8C00',
      MEDIOIN_COMPLETO: '#FFD700',
      MEDIO_COMPLETO: '#32CD32',
      SUPERIORIN_COMPLETO: '#4682B4',
      SUPERIOR_COMPLETO: '#00008B',
      MESTRADO: '#800080',
      DOUTORADO: '#4B0082',
      IGNORADO: '#808080',
    };

   
    this.data = {
      labels: this.labels,
      datasets: this.datasets.map((i) => ({
        label: i.label,
        data: i.data,
        
      })),
      
    };
    this.options = {
      responsive: true,
      scales: {
        x: {
          ticks: {
            color: 'white',
          },
        },
        y: {
          ticks: {
            color: 'white', 
          },
        },
      },
      plugins: {
        legend: {
          labels: {
            color: 'white',
          },
        },
      },
    };
  }
}
