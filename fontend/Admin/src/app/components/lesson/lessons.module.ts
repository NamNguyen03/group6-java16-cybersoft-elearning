import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LessonsRoutingModule } from './lessons-routing.module';
import { CreateLessonComponent } from './create-lesson/create-lesson.component';

import { ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { ListLessonComponent } from './list-lesson/list-lesson.component';
import { LessonInfoComponent } from './lesson-info/lesson-info.component';

@NgModule({
  declarations: [CreateLessonComponent,ListLessonComponent,LessonInfoComponent],
  imports: [
    CommonModule,
    LessonsRoutingModule,
    ReactiveFormsModule,
    NgbModule,
    Ng2SmartTableModule,
    AngularEditorModule 
  ]
})
export class LessonsModule { }
