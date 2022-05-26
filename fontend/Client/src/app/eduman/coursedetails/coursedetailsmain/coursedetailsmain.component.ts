import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FeedBackClient } from 'src/app/api-clients/feedback.client';
import { CourseClient } from 'src/app/api-clients/course.client';
import { CourseRp } from 'src/app/api-clients/model/course.model';
import { UserService } from 'src/app/share/user/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommentCreate } from 'src/app/api-clients/model/feedback.model';

@Component({
  selector: 'app-coursedetailsmain',
  templateUrl: './coursedetailsmain.component.html',
  styleUrls: ['./coursedetailsmain.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CoursedetailsmainComponent implements OnInit {
  public formComment :FormGroup;

  writeReviewActive:boolean=false;
  private course = new  CourseRp("zxc2123zc-zxc213zxc12a-zxc","JAVA 123","DEVELOPMENT",2,"https://firebasestorage.googleapis.com/v0/b/e-learning-5efea.appspot.com/o/1210fd36-dc67-42ae-b88a-168f513d41b1.png?alt=media&token=1210fd36-dc67-42ae-b88a-168f513d41b1.png",4,"vzvcbvzasdzxczasdsda asdsadxcz  ads   asda","admin1",12,"BEGINNER","zcxz?zca","zcsxcahahhahaha","jaaggddgbxcz"); 
  writeReview(){
    if(this.writeReviewActive==false){
      this.writeReviewActive=true;
    }
    else {
      this.writeReviewActive=false;
    }
  }
  constructor(private formBuilder:FormBuilder,
              private feedbackClient: FeedBackClient,
              private route: ActivatedRoute
    ) { 
      this.formComment = this.formBuilder.group({
        comment: [''],
      });
    }
  
    ngOnInit(): void {
    
  }

  writeComment(){
    let comment = this.formComment.controls['comment'].value;
    this.route.queryParams.subscribe(params => {
      let lessonId = params['lessonId'] == undefined ? null: params['lessonId'];
      let userId = params['lessonId'] == undefined ? null: params['userId'];
      this.feedbackClient.writeComment(new CommentCreate(comment,userId,lessonId))
    });
  }

}
