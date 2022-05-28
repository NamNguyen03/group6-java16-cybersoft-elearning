import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CourseClient } from 'src/app/api-clients/course.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseRp } from 'src/app/api-clients/model/course.model';

@Component({
  selector: 'app-coursetabtwo',
  templateUrl: './coursetabtwo.component.html',
  styleUrls: ['./coursetabtwo.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CoursetabtwoComponent implements OnInit {
  public coursesAll: CourseRp[] = [];
  public coursesDevelopment: CourseRp[] = [];
  public coursesDataScience: CourseRp[] = [];
  public coursesDesign: CourseRp[] = [];
  constructor(private _courseClient: CourseClient) { }

  ngOnInit(): void {
    this.getAllCourseRp();
    this.getDevelopmentCourseRp();
    this.getDataScienceCourseRp();
    this.getDesignCourseRp();
  }

  getDesignCourseRp(): void {
    this._courseClient.searchRequest(new PageRequest(1, 6, 0, 0, [], ['DESIGN'])).subscribe(
      response =>{
        this.coursesDesign = response.content.items || [];
      }
    )
  }

  getDataScienceCourseRp(): void {
    this._courseClient.searchRequest(new PageRequest(1, 6, 0, 0, [], ['DATA_SCIENCE'])).subscribe(
      response =>{
        this.coursesDataScience = response.content.items || [];
      }
    )
  }

  getDevelopmentCourseRp(): void{
    this._courseClient.searchRequest(new PageRequest(1, 6, 0, 0, [], ['DEVELOPMENT'])).subscribe(
      response =>{
        this.coursesDevelopment = response.content.items || [];

      }
    )
  }

  getAllCourseRp(): void {
    this._courseClient.searchRequest(new PageRequest(1, 6, 0, 0, [], [])).subscribe(
      response => {
        this.coursesAll = response.content.items || [];
      }
    )
  }

}
