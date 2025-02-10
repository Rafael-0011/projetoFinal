import { Component, Input, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-chart-pie',
  templateUrl: './chart-pie.component.html',
  styleUrl: './chart-pie.component.css',
  standalone: false,
})
export class ChartPieComponent {
  data!: any;

  @Input() labels: string[] = [];
  @Input() datasets: { label: string[]; data: number[] }[] = [];

  ngOnChanges(changes: SimpleChanges) {
    if (changes['labels'] || changes['datasets']) {
      this.updateChart();
    }
  }

  updateChart() {
    this.data = {
      labels: this.labels,
      datasets: this.datasets.map((ds) => ({
        label: ds.label.join(', '), // 🔹 Concatena os labels do array em uma única string
        data: ds.data,
      })),
    };
  }
}
