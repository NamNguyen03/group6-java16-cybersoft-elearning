import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FeedBackClient } from 'src/app/api-clients/feedback.client';
import { CommentCreate, CommentResponse } from 'src/app/api-clients/model/feedback.model';
import { UserService } from 'src/app/share/user/user.service';

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
    private form: FormBuilder,
    ) {
      this.formComment = this.form.group({
        comment: [''],
      })
     }
    

  ngOnInit(): void {
   this.findComment();
    
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
  
}
