import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseClient } from 'src/app/api-clients/course.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseRp } from 'src/app/api-clients/model/course.model';

@Component({
  selector: 'app-coursebar',
  templateUrl: './coursebar.component.html',
  styleUrls: ['./coursebar.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CoursebarComponent implements OnInit {
  public searchForm: FormGroup;
  public course_list:CourseRp[] =[];
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
          this.course_list =  response.content.items || [];
      }
      
    );  
    }) 


  }
  search(){
    let fieldNameSort = this.searchForm.controls['fieldNameSort'].value;
    let isIncrementSort = this.searchForm.controls['isIncrementSort'].value;
    let fieldNameSearch = this.searchForm.controls['fieldNameSearch'].value;
    let valueFieldNameSearch = this.searchForm.controls['valueFieldNameSearch'].value;
    this._router.navigate(['/course/coursetwomain'],{
      queryParams: {'fieldNameSort':fieldNameSort,'isIncr ementSort':isIncrementSort,'fieldNameSearch':fieldNameSearch,'valueFieldNameSearch':valueFieldNameSearch}
      
    })
    
  }

}
