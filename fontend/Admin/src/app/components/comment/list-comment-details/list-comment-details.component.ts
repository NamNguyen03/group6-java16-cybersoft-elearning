import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute} from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CommentLesson } from 'src/app/api-clients/model/status-comment.model';
import { StatusCommentClient } from 'src/app/api-clients/status-comment.client';

@Component({
  selector: 'app-list-comment-details',
  templateUrl: './list-comment-details.component.html',
  styleUrls: ['./list-comment-details.component.scss']
})
export class ListCommentDetailsComponent implements OnInit {

  public comments: CommentLesson[] = [];
  public courseName: string = '';
  public displayName: string = '';
  public status: string = '';
  private id: string = '';

  constructor(
    private statusCommentClient: StatusCommentClient, 
    private _toastr: ToastrService, 
    private _statusCommentClient: StatusCommentClient,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.getData();
  }

  private getData(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.statusCommentClient.getById(this.id).subscribe((response) => {
      let list = [];
      response.content.course.lessons.map(lesson => {
        lesson.comments.map(comment => list.push(new CommentLesson(comment.id, comment.content, lesson.name)))
      });
      this.comments = list;
      this.courseName = response.content.course.courseName;
      this.displayName = response.content.user.displayName;
      this.status = response.content.status;
    })
  }

  public settings = {
    pager: {
        display: true,
        perPage: 100,
    },
    actions: {
        custom: false,
        delete: true,
        add: false,
    },
    columns: {
        content: {
          title:'Content'
        },
        lessonName: {
          title:'Lesson Name'
        }
    },
  };

  onChange(status: string): void {
    this._statusCommentClient.update(this.id, status).subscribe(
      () =>{
        this._toastr.success('Update status successfully', 'Success');
        this.getData();
      }
    )
  }
}
