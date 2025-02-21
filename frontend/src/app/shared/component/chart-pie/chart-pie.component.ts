import { Component, Input, SimpleChanges } from '@angular/core';
import { StatusEnum } from '../../../module/Enumerate/status-enum';

@Component({
  selector: 'app-chart-pie',
  templateUrl: './chart-pie.component.html',
  styleUrl: './chart-pie.component.css',
  standalone: false,
})
export class ChartPieComponent {
  data!: any;

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
      AGUARDANDO: '#1982C4',
      APROVADO: '#8AC926',
      REPROVADO: '#FF595E',
    };

    const backgroundColors = this.labels.map(
      (label) => colorMap[label] || 'black'
    );

    this.data = {
      labels: this.labels,
      datasets: this.datasets.map((i) => ({
        label: i.label,
        data: i.data,
        backgroundColor: backgroundColors,
      })),
    };

    this.options = {
      responsive: true,
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
