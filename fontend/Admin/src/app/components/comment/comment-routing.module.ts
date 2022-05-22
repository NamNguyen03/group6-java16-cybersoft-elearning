
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ListCommentDetailsComponent } from "./list-comment-details/list-comment-details.component";
import { ListStatusCommentComponent } from "./list-status-comment/list-status-comment.component";

const routes: Routes = [
    {
      path: '',
      children: [
            {
                path: 'list-comment-details',
                component: ListCommentDetailsComponent,
                data: {
                    title: "List Comment Details",
                    breadcrumb: "List Comment Details"
                }
            },
            {
                path: 'list-status-comment',
                component: ListStatusCommentComponent,
                data: {
                    title: "List Status Comment",
                    breadcrumb: "List Status Comment"
                }
            },
        ]
    }
  ];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CommentRoutingModule { }
  