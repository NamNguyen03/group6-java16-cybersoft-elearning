import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ReactiveFormsModule } from '@angular/forms';
import { CKEditorModule } from 'ngx-ckeditor';

import { CreateGroupComponent } from './create-group/create-group.component';
import { ListGroupComponent } from './list-group/list-group.component';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { PagesRoutingModule } from '../pages/pages-routing.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { GroupRoutingModule } from './group-routing.module';

@NgModule({
  declarations: [ListGroupComponent, CreateGroupComponent],
  imports: [
    CommonModule,
    GroupRoutingModule,
    NgbModule,
    ReactiveFormsModule,
    CKEditorModule,
    NgxDatatableModule,
    Ng2SmartTableModule
  ]
})
export class GroupModule { }
