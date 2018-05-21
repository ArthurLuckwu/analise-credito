import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalhesPortadorComponent } from './detalhes-portador.component';

describe('DetalhesPortadorComponent', () => {
  let component: DetalhesPortadorComponent;
  let fixture: ComponentFixture<DetalhesPortadorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalhesPortadorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalhesPortadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
