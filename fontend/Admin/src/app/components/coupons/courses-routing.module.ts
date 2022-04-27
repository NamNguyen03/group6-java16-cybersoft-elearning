import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListCourseComponent } from './list-course/list-course.component';
import { CreateCourseComponent } from './create-course/create-course.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'list-course',
        component: ListCourseComponent,
        data: {
          title: "List Course",
          breadcrumb: "List Course"
        }
      },
      {
        path: 'create-course',
        component: CreateCourseComponent,
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
export class CoursesRoutingModule { }
