import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FeedBackClient } from 'src/app/api-clients/feedback.client';
import { CommentResponse } from 'src/app/api-clients/model/feedback.model';
import { UserService } from 'src/app/share/user/user.service';

@Component({
  selector: 'app-coursereview',
  templateUrl: './coursereview.component.html',
  styleUrls: ['./coursereview.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CoursereviewComponent implements OnInit {
  public listComments : CommentResponse[] =[];
  private lessonId : string ="5e10f993-f2bf-4f68-b4c1-92b5d02e4133";
  private userId : string ="";
  private comment: string ="";

  constructor(private route: ActivatedRoute,
    private _router: Router,
    private userService: UserService,
    private feedBackClient: FeedBackClient ) { }
    

  ngOnInit(): void {
   this.findComment();
    
  }

  findComment(){
    
      this.feedBackClient.findComment(this.lessonId).subscribe(
        response => 
         this.listComments = response.content);
     
  }
  writeComment(){
   
  }
}
