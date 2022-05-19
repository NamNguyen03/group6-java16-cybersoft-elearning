import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListRoleComponent } from './list-role/list-role.component';
import { CreatePageComponent } from './create-role/create-role.component';
import { RoleDetailsComponent } from './role-details/role-details.component';

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
      },
      {
        path: 'role-details',
        component: RoleDetailsComponent,
        data: {
          title: "Role details",
          breadcrumb: "Role details"
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
