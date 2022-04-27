import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';
import { CKEditorModule } from 'ngx-ckeditor';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';


import { PagesRoutingModule } from './roles-routing.module';
import { ListRoleComponent } from './list-role/list-role.component';
import { CreatePageComponent } from './create-role/create-role.component';
import { Ng2SmartTableModule } from 'ng2-smart-table';

@NgModule({
  declarations: [ListRoleComponent, CreatePageComponent],
  imports: [
    CommonModule,
    PagesRoutingModule,
    NgbModule,
    ReactiveFormsModule,
    CKEditorModule,
    NgxDatatableModule,
    Ng2SmartTableModule

  ]
})
export class RolesModule { }
