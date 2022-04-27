import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListPageComponent } from './list-page/list-page.component';
import { CreatePageComponent } from './create-page/create-page.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'list-role',
        component: ListPageComponent,
        data: {
          title: "List Role",
          breadcrumb: "List Role"
        }
      },
      {
        path: 'create-role',
        component: CreatePageComponent,
        data: {
          title: "Create Role",
          breadcrumb: "Create Role"
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule { }
