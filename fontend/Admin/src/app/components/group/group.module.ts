import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CKEditorModule } from 'ngx-ckeditor';

import { CreateGroupComponent } from './create-group/create-group.component';
import { ListGroupComponent } from './list-group/list-group.component';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { GroupRoutingModule } from './group-routing.module';
import { GroupDetailsComponent } from './group-details/group-details.component';

@NgModule({
  declarations: [ListGroupComponent, CreateGroupComponent,GroupDetailsComponent],
  imports: [
    CommonModule,
    GroupRoutingModule,
    NgbModule,
    ReactiveFormsModule,
    CKEditorModule,
    NgxDatatableModule,
    Ng2SmartTableModule,
    FormsModule
  ]
})
export class GroupModule { }
