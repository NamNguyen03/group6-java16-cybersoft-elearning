import { Component, OnInit } from '@angular/core';
import { LessonClient } from 'src/app/api-clients/lesson.client';
import { FormBuilder } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { FormGroup } from '@angular/forms';
import { PageService } from 'src/app/shared/service/page/page.service';

@Component({
  selector: 'app-list-lesson',
  templateUrl: './list-lesson.component.html',
  styleUrls: ['./list-lesson.component.scss']
})
export class ListLessonComponent implements OnInit {
  public lesson_list = [];
  public selected = [];
  public searchForm: FormGroup;
  public isSearch = false;
  public pageCurrent = 1;
  public pages = [];

  _pageRequest: PageRequest = new PageRequest(1,10,
    null,
    true,
    null,
    null);

  constructor(private lessonClient: LessonClient,
    private form: FormBuilder,
    private _pageService: PageService,
    private _router: Router,
    private route: ActivatedRoute) {
      this.searchForm = this.form.group({
        fieldNameSort:[''],
        isIncrementSort:[''],
        fieldNameSearch:[''],
        valueFieldNameSearch: ['']
        })
  }

  public settings = {
    pager: {
        display: true,
        perPage: 10,
    },
    actions: {
        custom: false,
    },
    columns: {

      name: {
        title: 'Lesson Name',
        editable: false,
      },
      description: {
        title: 'Description',
        editable: false,
      },
      content: {
        title: 'Content',
        editable: false,
      }
    },

  };

  ngOnInit() {
    this.loadData();
  }

  getPageDetails(): void{
   
    this.route.queryParams.subscribe(params => {
      let fieldNameSort = params['fieldNameSort'] == undefined ? null: params['fieldNameSort'];
      let isIncrementSort = params['isIncrementSort'] == (undefined||null) ? true : params['isIncrementSort'];
      let fieldNameSearch = params['fieldNameSearch'] == undefined ? '': params['fieldNameSearch'];
      let valueFieldNameSearch = params['valueFieldNameSearch'] == undefined ? '': params['valueFieldNameSearch'];
      this.pageCurrent = params['page'] == undefined ? 1 : params['page'];
      this._pageRequest = new PageRequest(this.pageCurrent, 10, fieldNameSort, isIncrementSort, fieldNameSearch, valueFieldNameSearch);
      this.loadData();

  });
  }

  loadData() {
    
      this.lessonClient.searchRequest(this._pageRequest).subscribe(
        response =>{
          this.lesson_list = response.content.items;
          this.pages = this._pageService.getPager(response.content.pageCurrent, response.content.totalPage);

      });  
    }
  

  onSelect({ selected }) {
    this.selected.splice(0, this.selected.length);
    this.selected.push(...selected);
  }

  toggleSearch(){
    this.isSearch=!this.isSearch;
  }

  search(){
    let fieldNameSort = this.searchForm.controls['fieldNameSort'].value == "NONE" ? null :this.searchForm.controls['fieldNameSort'].value;
    let isIncrementSort = this.searchForm.controls['isIncrementSort'].value;
    let fieldNameSearch = this.searchForm.controls['fieldNameSearch'].value== "NONE" ? null :this.searchForm.controls['fieldNameSearch'].value;
    let valueFieldNameSearch = this.searchForm.controls['valueFieldNameSearch'].value;
    this._router.navigate(['/lessons/list-lesson'],{
      queryParams: {
        'page':this.pageCurrent,
        'fieldNameSort':fieldNameSort,
        'isIncrementSort':isIncrementSort,
        'fieldNameSearch':fieldNameSearch,
        'valueFieldNameSearch':valueFieldNameSearch}

    })
    
  }
  clickPage(index: string): void {
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

}
