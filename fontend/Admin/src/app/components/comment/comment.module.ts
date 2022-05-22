import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListStatusCommentComponent } from './list-status-comment/list-status-comment.component';
import { ListCommentDetailsComponent } from './list-comment-details/list-comment-details.component';
import { CommentRoutingModule } from './comment-routing.module';



@NgModule({
  declarations: [
    ListStatusCommentComponent,
    ListCommentDetailsComponent
  ],
  imports: [
    CommentRoutingModule,
    CommonModule
  ]
})
export class CommentModule { }
