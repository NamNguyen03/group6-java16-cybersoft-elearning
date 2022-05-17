import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseClient } from 'src/app/api-clients/course.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseRp } from 'src/app/api-clients/model/course.model';

@Component({
  selector: 'app-coursetwomain',
  templateUrl: './coursetwomain.component.html',
  styleUrls: ['./coursetwomain.component.scss']
})
export class CoursetwomainComponent implements OnInit {
  public course_list:CourseRp[] =[];
  public searchForm: FormGroup;
  pageRequet: PageRequest = new PageRequest(1,10,
    'name',
    true,
    'name',
    '');
  constructor(private form: FormBuilder,
    private route: ActivatedRoute,
    private _router: Router,
    private courseClient:CourseClient,
    
    
  ) {
    this.searchForm = this.form.group({
      fieldNameSort:['name'],
      isIncrementSort:['true'],
      fieldNameSearch:['name'],
      valueFieldNameSearch: ['']
   })
  }

  course1 = { "img" : "https://firebasestorage.googleapis.com/v0/b/e-learning-5efea.appspot.com/o/730e7bc5-ffc1-48ec-84a1-e2765aafc1e1.jpeg?alt=media&token=730e7bc5-ffc1-48ec-84a1-e2765aafc1e1.jpeg",
              "name" : "Name course Cat",
              "starAVG" : 4,
              "totalTime" : 5,
              "level" : "master",
              "description" : "Description for the course",
              "skills": ["html" , "js" , "css"],
              "category" : "development"};

  course2 = { "img" : "https://firebasestorage.googleapis.com/v0/b/e-learning-5efea.appspot.com/o/730e7bc5-ffc1-48ec-84a1-e2765aafc1e1.jpeg?alt=media&token=730e7bc5-ffc1-48ec-84a1-e2765aafc1e1.jpeg",
              "name" : "Name course Cat 2" ,
              "starAVG" : 3,
              "totalTime" : 5,
              "level" : "master",
              "description" : "Description for the course",
              "skills": ["html" , "js" , "css"],
              "category" : "development"};
   public courses = [this.course1 , this.course1, this.course2 , this.course2,
     this.course1 , this.course1, this.course2 , this.course2];

 
  ngOnInit(): void {
    this.loadData();
  }
  loadData() {
    this.route.queryParams.subscribe(params =>{
      let fieldNameSort = params['fieldNameSort'] == undefined ? null: params['fieldNameSort'];
      let isIncrementSort = params['isIncrementSort'] == (undefined||null) ? true : params['isIncrementSort'];
      let fieldNameSearch = params['fieldNameSearch'] == undefined ? '': params['fieldNameSearch'];
      let valueFieldNameSearch = params['valueFieldNameSearch'] == undefined ? '': params['valueFieldNameSearch'];

      this.pageRequet = new PageRequest(1,10,fieldNameSort,isIncrementSort,fieldNameSearch,valueFieldNameSearch)
      console.log(this.pageRequet);
      this.courseClient.searchRequest(this.pageRequet).subscribe(
        response =>{
          this.course_list = response.content.items||[];
      }
      
    );  
    }) 


  }
}
  
