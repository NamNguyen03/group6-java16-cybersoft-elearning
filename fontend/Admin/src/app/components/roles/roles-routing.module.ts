import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListRoleComponent } from './list-role/list-role.component';
import { CreatePageComponent } from './create-role/create-role.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'list-role',
        component: ListRoleComponent,
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
