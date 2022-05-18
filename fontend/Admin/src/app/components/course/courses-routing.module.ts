import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListCourseComponent } from './list-course/list-course.component';
import { CreateCourseComponent } from './create-course/create-course.component';
import { LessonInfoComponent } from '../lesson-info/lesson-info.component';
import { CourseDetailsComponent } from './course-details/course-details.component';

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
      },
      {
        path: 'course-details',
        component: CourseDetailsComponent,
        data: {
          title: "Course Details",
          breadcrumb: "Course Details"
        }
      }
      ,
      {
        path: 'lesson-info',
        component: LessonInfoComponent,
        data: {
          title: "Course Details",
          breadcrumb: "Course Details"
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
