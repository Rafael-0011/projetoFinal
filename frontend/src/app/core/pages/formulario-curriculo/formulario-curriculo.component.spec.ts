import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioCurriculoComponent } from './formulario-curriculo.component';

describe('FormularioCurriculoComponent', () => {
  let component: FormularioCurriculoComponent;
  let fixture: ComponentFixture<FormularioCurriculoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioCurriculoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormularioCurriculoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
