import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShopPagingComponent } from './shop-paging.component';

describe('ShopPagingComponent', () => {
  let component: ShopPagingComponent;
  let fixture: ComponentFixture<ShopPagingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShopPagingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShopPagingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
