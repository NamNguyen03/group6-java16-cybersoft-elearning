import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListStatusCommentComponent } from './list-status-comment/list-status-comment.component';
import { ListCommentDetailsComponent } from './list-comment-details/list-comment-details.component';
import { CommentRoutingModule } from './comment-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [
    ListStatusCommentComponent,
    ListCommentDetailsComponent
  ],
  imports: [
    CommonModule,
    NgbModule,
    Ng2SmartTableModule,
    ReactiveFormsModule,
    CommentRoutingModule,
    FormsModule
  ]
})
export class CommentModule { }
