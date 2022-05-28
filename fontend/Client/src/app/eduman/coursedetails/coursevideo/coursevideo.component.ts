import { Component, OnInit, ViewEncapsulation } from '@angular/core';
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
  public coursesDevelopment: CourseRp[] = [];
  public coursesDataScience: CourseRp[] = [];
  public coursesDesign: CourseRp[] = [];
  constructor(private _courseClient: CourseClient) { }

  ngOnInit(): void {
    this.getAllCourseRp();
  }


  getAllCourseRp(): void {
    this._courseClient.searchRequest(new PageRequest(1, 6, 0, 0, [], [])).subscribe(
      response => {
        console.log(response);
        
        this.coursesAll = response.content.items || [];
      }
    )
  }

}
