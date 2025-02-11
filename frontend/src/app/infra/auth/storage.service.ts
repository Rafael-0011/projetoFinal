import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private stotage: Storage;

  constructor() {
    this.stotage = window.localStorage;
  }

  set(key: string, value: any): boolean {
    if (this.stotage) {
      this.stotage.setItem(key,value);
    }
    return false;
  }

  get(key: string): any {
    if (this.stotage) {
      return JSON.parse(this.stotage.getItem(key) || 'null');
    }
    return null;
  }

  remove(key: string): boolean {
    if (this.stotage) {
      this.stotage.removeItem(key);
    }
    return false;
  }
  clear(key: string): boolean {
    if (this.stotage) {
      this.stotage.clear();
    }
    return false;
  }
}
