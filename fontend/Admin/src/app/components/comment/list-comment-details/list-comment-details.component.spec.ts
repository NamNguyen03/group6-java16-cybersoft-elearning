import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCommentDetailsComponent } from './list-comment-details.component';

describe('ListCommentDetailsComponent', () => {
  let component: ListCommentDetailsComponent;
  let fixture: ComponentFixture<ListCommentDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListCommentDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListCommentDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
