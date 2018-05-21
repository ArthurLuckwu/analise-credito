import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroPortadorComponent } from './cadastro-portador.component';

describe('CadastroPortadorComponent', () => {
  let component: CadastroPortadorComponent;
  let fixture: ComponentFixture<CadastroPortadorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastroPortadorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastroPortadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
