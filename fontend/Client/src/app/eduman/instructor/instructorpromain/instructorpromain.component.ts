import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CourseRp } from 'src/app/api-clients/model/course.model';
import { InstructorCourseClientDTO, UserRp } from 'src/app/api-clients/model/user.model';
import { UserClient } from 'src/app/api-clients/user.client';

@Component({
  selector: 'app-instructorpromain',
  templateUrl: './instructorpromain.component.html',
  styleUrls: ['./instructorpromain.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class InstructorpromainComponent implements OnInit {
  followedActive:boolean=false;
  btnVal = "Follow";
  
  public myProfile: InstructorCourseClientDTO = new InstructorCourseClientDTO();

  public myCourse:CourseRp[] = [];

  public myCourse2 : CourseRp[] = [];

  
  public isShow3 = true;
  public isShowAll = false;
  public isNotShow = true;

  followedClick(){
    if(this.followedActive==false){
      this.followedActive=true;
      this.btnVal = "Followed"
    }
    else {
      this.followedActive=false;
      this.btnVal = "Follow"
    }
  }

  constructor(
    private userClient: UserClient,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.getData()
  }

  getData(): void {
    this.route.params.subscribe((params) => {
      let username = params["username"];
      this.userClient.getMyProfileFindUserName(username).subscribe(
        response => {
          this.myProfile = response.content

          this.myCourse = this.myProfile.courses==undefined ?  [] : this.myProfile.courses

          if (this.myCourse.length < 3) {
            this.myCourse2 = this.myCourse;
          } else {
            for (let i = 0; i < 3; i++) {
              this.myCourse2[i] = this.myCourse[i];
            }
          }
          this.myCourse2;   
        }
      )
    })
  }

  toggleShow() {
    this.isShow3 = !this.isShow3;
    this.isShowAll = !this.isShowAll;
    this.isNotShow = !this.isNotShow;
  }
      
}
