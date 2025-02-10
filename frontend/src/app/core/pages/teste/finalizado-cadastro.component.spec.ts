import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalizadoCadastroComponent } from './finalizado-cadastro.component';

describe('FinalizadoCadastroComponent', () => {
  let component: FinalizadoCadastroComponent;
  let fixture: ComponentFixture<FinalizadoCadastroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FinalizadoCadastroComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FinalizadoCadastroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
