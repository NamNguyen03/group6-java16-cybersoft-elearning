import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { FeedBackClient } from 'src/app/api-clients/feedback.client';
import { LessonClient } from 'src/app/api-clients/lesson.client';
import { CourseDetailsReponseClientDTO } from 'src/app/api-clients/model/course.model';
import { CommentCreate, CommentResponse } from 'src/app/api-clients/model/feedback.model';
import { UserRp } from 'src/app/api-clients/model/user.model';
import { UserService } from 'src/app/share/user/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-coursereview',
  templateUrl: './coursereview.component.html',
  styleUrls: ['./coursereview.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CoursereviewComponent implements OnInit {
  public listComments : CommentResponse[] =[];
  formComment: FormGroup
  writeReviewActive: boolean = false;
  public myComment :string =''
  private idLesson ='';
  public userCurrent :UserRp=new UserRp();
  public authorCourse :string| undefined ='';

  writeReview() {
    if (this.writeReviewActive == false) {
      this.writeReviewActive = true;
    }
    else {
      this.writeReviewActive = false;
    }
  }

  constructor(private route: ActivatedRoute,
    private feedBackClient: FeedBackClient,
    private lessonClient:LessonClient,
    private form: FormBuilder,
    private _toastr:ToastrService,
    private userService: UserService
    ) {
      this.userService.$userCurrent.subscribe(user => {
        this.userCurrent = user

      })
      this.formComment = this.form.group({
        comment: [''],
      })
     }
    

  ngOnInit(): void {
   this.findComment();
   this.lessonClient.getLessonDetails(this.idLesson).subscribe(
    response =>
    this.authorCourse = response.content.course?.createdBy
    );
    
  }

  findComment(){
    this.route.params.subscribe((params) => {
      this.idLesson = params["id"];
      this.feedBackClient.findComment(this.idLesson).subscribe(
        response => 
          this.listComments = response.content
        
        ) ;
      })
  }
  postComment(){
    this.myComment=this.formComment.controls['comment'].value;
    this.feedBackClient.writeComment(new CommentCreate(this.myComment,this.idLesson)).subscribe( ()=> 
      this.findComment()
    );
  
  }
  deleteComment(id:string){
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, Delete it!',
    }).then((result) => {
         if (result.isConfirmed) {
        this.feedBackClient.deleteComment(id).subscribe(
          () => {
           this._toastr.success('Success','Delete Comment Successfully')
           this.findComment();
          }
        )
       
      }})
    

  }
  
}
