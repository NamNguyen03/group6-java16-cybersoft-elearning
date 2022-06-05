import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListGroupComponent } from './list-group/list-group.component';
import { CreateGroupComponent } from './create-group/create-group.component';
import { GroupDetailsComponent } from './group-details/group-details.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'list-group',
        component: ListGroupComponent,
        data: {
          title: "Group List",
          breadcrumb: "Group List"
        }
      },
      {
        path: 'create-group',
        component: CreateGroupComponent,
        data: {
          title: "Create Group",
          breadcrumb: "Create Group"
        }
      },
      {
        path: 'group-details',
        component: GroupDetailsComponent,
        data: {
          title: "Group Details",
          breadcrumb: "Group Details"
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GroupRoutingModule { }
