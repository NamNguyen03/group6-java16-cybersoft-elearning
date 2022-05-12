import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListUserComponent } from './list-user/list-user.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { AddGroupComponent } from './add-group/add-group.component';


const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'list-user',
        component: ListUserComponent,
        data: {
          title: "User List",
          breadcrumb: "User List"
        }
      },
      {
        path: 'create-user',
        component: CreateUserComponent,
        data: {
          title: "Create User",
          breadcrumb: "Create User"
        }
      },
      {
        path: 'user-details',
        component: UserDetailsComponent,
        data: {
          title: "User Details",
          breadcrumb: "User Details"
        }
      },
      {
        path: 'add-group',
        component: AddGroupComponent,
        data: {
          title: "Add Group",
          breadcrumb: "Add Group"
        }
      }

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule { }
