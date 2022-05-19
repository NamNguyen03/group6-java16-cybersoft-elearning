import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { CoursesRoutingModule } from './courses-routing.module';
import { ListCourseComponent } from './list-course/list-course.component';
import { CreateCourseComponent } from './create-course/create-course.component';
import { CKEditorModule } from 'ngx-ckeditor';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { CourseDetailsComponent } from './course-details/course-details.component';
import { UpdateCourseComponent } from './update-course/update-course.component';

@NgModule({
  declarations: [ListCourseComponent, CreateCourseComponent,CourseDetailsComponent,UpdateCourseComponent],
  imports: [
    CommonModule,
    CoursesRoutingModule,
    NgbModule,
    CKEditorModule,
    ReactiveFormsModule,
    NgxDatatableModule,
    Ng2SmartTableModule
  ]
})
export class CoursesModule { }
