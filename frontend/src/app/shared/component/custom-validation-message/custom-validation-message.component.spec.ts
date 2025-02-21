import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomValidationMessageComponent } from './custom-validation-message.component';

describe('CustomValidationMessageComponent', () => {
  let component: CustomValidationMessageComponent;
  let fixture: ComponentFixture<CustomValidationMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomValidationMessageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomValidationMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
