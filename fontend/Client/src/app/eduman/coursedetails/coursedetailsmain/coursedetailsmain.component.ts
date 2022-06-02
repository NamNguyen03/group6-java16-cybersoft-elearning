import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FeedBackClient } from 'src/app/api-clients/feedback.client';
import { LessonClient } from 'src/app/api-clients/lesson.client';
import { AuthorRp } from 'src/app/api-clients/model/author.model';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseDetailsReponseClientDTO, CourseRp } from 'src/app/api-clients/model/course.model';
import { CommentCreate } from 'src/app/api-clients/model/feedback.model';
import { CardLessonReponseClientDTO, LessonDetailsResponseClientDTO, LessonRp } from 'src/app/api-clients/model/lesson.model';

@Component({
  selector: 'app-coursedetailsmain',
  templateUrl: './coursedetailsmain.component.html',
  styleUrls: ['./coursedetailsmain.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CoursedetailsmainComponent implements OnInit {
   
  public listCardLesson: CardLessonReponseClientDTO[] = [];
  public listCardLesson2: CardLessonReponseClientDTO[] = [];
  public listCardLessonResult: CardLessonReponseClientDTO[] = [];
  public courseDetail: CourseDetailsReponseClientDTO = new CourseDetailsReponseClientDTO();
  public author: AuthorRp = new AuthorRp();
  public lessonDetails: LessonDetailsResponseClientDTO = new LessonDetailsResponseClientDTO();
  public isShow = false;
  public isShowLesson = false;
  public isShowContent = false;
  public isHideContent = true;
  public isShow3 = true;
  public isShowAll = false;
  public isNotShow = true;
  constructor(
    private route: ActivatedRoute,
    private lessonClient: LessonClient,
  ) {}


  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.route.params.subscribe((params) => {
      let id = params["id"];
      this.lessonClient.getLessonDetails(id).subscribe(
        response => {

          this.lessonDetails = response.content;

          this.author = this.lessonDetails.author == null ? new AuthorRp() : this.lessonDetails.author;

          this.courseDetail = this.lessonDetails.course == null ? new CourseDetailsReponseClientDTO() : this.lessonDetails.course;

          this.listCardLesson = this.courseDetail.lessons == undefined ? [] : this.courseDetail.lessons;

          if (this.listCardLesson.length < 3) {
            this.listCardLesson2 = this.listCardLesson;
          } else {
            for (let i = 0; i < 3; i++) {
              this.listCardLesson2[i] = this.listCardLesson[i];
            }
          }

          this.listCardLesson2;
        }
      )
    });
  }

  toggleShow() {
    this.isShow3 = !this.isShow3;
    this.isShowAll = !this.isShowAll;
    this.isNotShow = !this.isNotShow;
  }
  toggleShowContent() {
    this.isShowContent = !this.isShowContent;
    this.isHideContent = !this.isHideContent;
  }

}
