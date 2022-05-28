import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseClient } from 'src/app/api-clients/course.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseRp } from 'src/app/api-clients/model/course.model';
import { PageService } from 'src/app/share/page/page.service';

@Component({
  selector: 'app-coursetwomain',
  templateUrl: './coursetwomain.component.html',
  styleUrls: ['./coursetwomain.component.scss']
})
export class CoursetwomainComponent implements OnInit {
  public course_list:CourseRp[] =[];
  public searchForm: FormGroup;
  private valueSearch = '';
  pageRequest: PageRequest = new PageRequest(1, 12, 0, 0, [], []);
  public pages: (string | number)[] = [];
  public pageCurrent = 1;
  constructor(private form: FormBuilder,
    private route: ActivatedRoute,
    private _router: Router,
    private courseClient: CourseClient,
    private _pageService: PageService
  ) {
    this.searchForm = this.form.group({
      fieldNameSort:['name'],
      isIncrementSort:['true'],
      fieldNameSearch:['name'],
      valueFieldNameSearch: ['']
   })
  }
 
  ngOnInit(): void {
    this.getPageDetails();
  }

  loadData() {
    
      this.courseClient.searchRequest(this.pageRequest).subscribe(
        response =>{
          this.course_list = response.content.items||[];
          this.pages = this._pageService.getPager(response.content.pageCurrent ? response.content.pageCurrent : 1 , response.content.totalPage ? response.content.totalPage : 1 );
      });  
    }

  getPageDetails(): void{
    this.route.queryParams.subscribe(params => {
      this.valueSearch = params['search'] == undefined ? '': params['search'];
      this.pageCurrent = params['page'] == undefined ? 1 : params['page'];
      this.pageRequest = new PageRequest(1, 12, 0, 0, [], []);
      this.loadData();

    });
  }



  clickPage(index: string | number): void {
    if(index == 'next'){
      this.pageCurrent++;
    }
    if(index == 'prev'){
      this.pageCurrent--;
    }
    if(index != 'prev' && index != 'next'){
      this.pageCurrent = Number(index);
    }
    this.search();
  }

  search(){
    this._router.navigate(['/course-2'],{
      queryParams: {
        'page':this.pageCurrent,
        'search': this.valueSearch
      }

    })
   }
   viewDetail(courseId:string|undefined){
    this._router.navigate(['/course-details'], {
      queryParams: { 'courseId': courseId }
    })

}}