import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStatusCommentComponent } from './list-status-comment.component';

describe('ListStatusCommentComponent', () => {
  let component: ListStatusCommentComponent;
  let fixture: ComponentFixture<ListStatusCommentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListStatusCommentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListStatusCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
