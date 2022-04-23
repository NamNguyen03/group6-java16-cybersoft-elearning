import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListCouponComponent } from './list-coupon/list-coupon.component';
import { CreateCouponComponent } from './create-coupon/create-coupon.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'list-course',
        component: ListCouponComponent,
        data: {
          title: "List Course",
          breadcrumb: "List Course"
        }
      },
      {
        path: 'create-course',
        component: CreateCouponComponent,
        data: {
          title: "Create Course",
          breadcrumb: "Create Course"
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CouponsRoutingModule { }
