import { Component, HostListener, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CourseClient } from 'src/app/api-clients/course.client';
import { LessonClient } from 'src/app/api-clients/lesson.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseDetailsReponseClientDTO, CourseRp } from 'src/app/api-clients/model/course.model';
import { CardLessonReponseClientDTO, LessonDetailsResponseClientDTO } from 'src/app/api-clients/model/lesson.model';

@Component({
  selector: 'app-coursevideo',
  templateUrl: './coursevideo.component.html',
  styleUrls: ['./coursevideo.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CoursevideoComponent implements OnInit {

  public coursesAll: CourseRp[] = [];
  public coursesList3: CourseRp[] = [];
  constructor(private _courseClient: CourseClient) { }

  ngOnInit(): void {
    this.getAllCourseRp();
  }


  getAllCourseRp(): void {
    this._courseClient.searchRequest(new PageRequest(1, 6, 0, 0, [], [])).subscribe(
      response => {
        
        this.coursesAll = response.content.items || [];

        for (let i = 0; i < 3; i++) {
          this.coursesList3[i] = this.coursesAll[i];
        }

        this.coursesList3;

        console.log(this.coursesList3);
      }
    )
  }


}
