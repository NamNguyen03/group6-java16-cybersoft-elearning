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
 
  ngOnInit(): void {
    this.loadData();
  }
  loadData() {
    this.route.queryParams.subscribe(params =>{
      let fieldNameSort = params['fieldNameSort'] == undefined ? 'courseName': params['fieldNameSort'];
      let isIncrementSort = params['isIncrementSort'] == (undefined||null) ? true : params['isIncrementSort'];
      let fieldNameSearch = params['fieldNameSearch'] == undefined ? 'courseName': params['fieldNameSearch'];
      let valueFieldNameSearch = params['valueFieldNameSearch'] == undefined ? '': params['valueFieldNameSearch'];

      this.pageRequet = new PageRequest(1,10,fieldNameSort,isIncrementSort,fieldNameSearch,valueFieldNameSearch)
      console.log(this.pageRequet);
      this.courseClient.searchRequest(this.pageRequet).subscribe(
        response =>{
          console.log(response)
          this.course_list = response.content.items||[];
      }
      
    );  
    }) 


  }
}
  
