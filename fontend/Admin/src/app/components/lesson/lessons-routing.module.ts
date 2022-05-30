import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreateLessonComponent } from './create-lesson/create-lesson.component';


const routes: Routes = [
  {
    path: '',
    children: [
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
