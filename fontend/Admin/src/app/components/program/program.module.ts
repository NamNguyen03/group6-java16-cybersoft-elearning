import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListProgramComponent } from './list-program/list-program.component';
import { CreateProgramComponent } from './create-program/create-program.component';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProgramRoutingModule } from './program-routing.module';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CKEditorModule } from 'ngx-ckeditor';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';






@NgModule({
  declarations: [
    ListProgramComponent,
    CreateProgramComponent
  ],
  imports: [
    CommonModule,
    ProgramRoutingModule,
    NgbModule,
    Ng2SmartTableModule,
    FormsModule,
    ReactiveFormsModule,
    CKEditorModule,
    NgxDatatableModule,
  ]
})
export class ProgramModule { }
