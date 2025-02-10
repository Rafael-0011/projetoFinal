import { Component, Input, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-chart-bar',
  templateUrl: './chart-bar.component.html',
  styleUrl: './chart-bar.component.css',
  standalone: false,
})
export class ChartBarComponent {
  basicData: any;

  @Input() labels: string[] = [];
  @Input() datasets: { label: string; data: number[] }[] = [];

  ngOnChanges(changes: SimpleChanges) {
    if (changes['labels'] || changes['datasets']) {
      this.updateChart();
    }
  }

  updateChart() {
    this.basicData = {
      labels: this.labels,
      datasets: this.datasets
    };
  }
}
