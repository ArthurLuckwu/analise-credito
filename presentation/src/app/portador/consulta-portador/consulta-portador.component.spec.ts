import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultaPortadorComponent } from './consulta-portador.component';

describe('CadastroPortadorComponent', () => {
  let component: ConsultaPortadorComponent;
  let fixture: ComponentFixture<ConsultaPortadorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsultaPortadorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultaPortadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
