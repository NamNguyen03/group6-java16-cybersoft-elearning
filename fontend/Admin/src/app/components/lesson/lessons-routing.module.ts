import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreateLessonComponent } from './create-lesson/create-lesson.component';
import { LessonInfoComponent } from './lesson-info/lesson-info.component';
import { ListLessonComponent } from './list-lesson/list-lesson.component';


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
      },
      {
        path: 'list-lesson',
        component: ListLessonComponent,
        data: {
          title: "List Lesson",
          breadcrumb: "List Lesson"
        }
      },
      {
        path: 'lesson-info/:lessonId',
        component: LessonInfoComponent,
        data: {
          title: "Info Lesson",
          breadcrumb: "Info Lesson"
        }
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class LessonsRoutingModule { }
