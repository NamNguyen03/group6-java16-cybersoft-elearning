import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreateProgramComponent } from './create-program/create-program.component';
import { ListProgramComponent } from './list-program/list-program.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'list-program',
        component: ListProgramComponent,
        data: {
          title: "List Program",
          breadcrumb: "List Program"
        }
      },
      {
        path: 'create-program',
        component: CreateProgramComponent,
        data: {
          title: "Create Program",
          breadcrumb: "Create Program"
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProgramRoutingModule { }
