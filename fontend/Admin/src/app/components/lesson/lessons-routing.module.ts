import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListLessonComponent } from './list-lesson/list-lesson.component';
import { CreateLessonComponent } from './create-lesson/create-lesson.component';


const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'list-lesson',
        component: ListLessonComponent,
        data: {
          title: "Lesson List",
          breadcrumb: "Lesson List"
        }
      },
      {
        path: 'create-lesson',
        component: CreateLessonComponent,
        data: {
          title: "Create Lesson",
          breadcrumb: "Create Lesson"
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class LessonsRoutingModule { }
