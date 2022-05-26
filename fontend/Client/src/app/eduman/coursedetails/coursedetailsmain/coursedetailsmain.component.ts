import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LessonClient } from 'src/app/api-clients/lesson.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseDetailsReponseClientDTO, CourseRp } from 'src/app/api-clients/model/course.model';
import { CardLessonReponseClientDTO, LessonDetailsResponseClientDTO, LessonRp } from 'src/app/api-clients/model/lesson.model';

@Component({
  selector: 'app-coursedetailsmain',
  templateUrl: './coursedetailsmain.component.html',
  styleUrls: ['./coursedetailsmain.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CoursedetailsmainComponent implements OnInit {
  
  writeReviewActive:boolean=false;
  writeReview(){
    if(this.writeReviewActive==false){
      this.writeReviewActive=true;
    }
    else {
      this.writeReviewActive=false;
    }
  }
  public listCardLesson : CardLessonReponseClientDTO[] = [];
  public courseDetail : CourseDetailsReponseClientDTO = new CourseDetailsReponseClientDTO();
  public lessonDetails: LessonDetailsResponseClientDTO = new LessonDetailsResponseClientDTO();
  constructor(
    private route: ActivatedRoute,
    private lessonClient: LessonClient,
  ) { }

  ngOnInit(): void {
          console.log(this.lessonDetails)
          console.log(this.courseDetail)
          console.log(this.listCardLesson)
    this.loadData();
  }

  loadData(): void {
    this.route.params.subscribe((params) => {
      let id = params["id"];
      this.lessonClient.getLessonDetails(id).subscribe(
        response => {

          this.lessonDetails = response.content;

          this.courseDetail = this.lessonDetails.course == null ? new CourseDetailsReponseClientDTO() : this.lessonDetails.course;
        
          this.listCardLesson = this.courseDetail.lessons == undefined ? [] : this.courseDetail.lessons;

          console.log(this.lessonDetails)
          console.log(this.courseDetail)
          console.log(this.listCardLesson)
        }
      )
    });
  }
}
