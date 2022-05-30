import { Component, OnInit } from '@angular/core';
import { LessonClient } from 'src/app/api-clients/lesson.client';
import { FormBuilder } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { FormGroup } from '@angular/forms';

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
  pageRequet: PageRequest = new PageRequest(1,10,
    null,
    true,
    null,
    null);

  constructor(private lessonClient: LessonClient,
    private form: FormBuilder,
    private toastr: ToastrService,
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

  loadData() {
    this.route.queryParams.subscribe(params =>{
      let fieldNameSort = params['fieldNameSort'] == undefined ? null: params['fieldNameSort'];
      let isIncrementSort = params['isIncrementSort'] == (undefined||null) ? true : params['isIncrementSort'];
      let fieldNameSearch = params['fieldNameSearch'] == undefined ? '': params['fieldNameSearch'];
      let valueFieldNameSearch = params['valueFieldNameSearch'] == undefined ? '': params['valueFieldNameSearch'];

      this.pageRequet = new PageRequest(1,10,fieldNameSort,isIncrementSort,fieldNameSearch,valueFieldNameSearch)
      console.log(this.pageRequet)
      this.lessonClient.searchRequest(this.pageRequet).subscribe(
        response =>{
          this.lesson_list = response.content.items;
      }
      
    );  
    })
  }

  onSelect({ selected }) {
    this.selected.splice(0, this.selected.length);
    this.selected.push(...selected);
  }

  toggleSearch(){
    this.isSearch=!this.isSearch;
  }

  search(){
    let fieldNameSort = this.searchForm.controls['fieldNameSort'].value;
    let isIncrementSort = this.searchForm.controls['isIncrementSort'].value;
    let fieldNameSearch = this.searchForm.controls['fieldNameSearch'].value;
    let valueFieldNameSearch = this.searchForm.controls['valueFieldNameSearch'].value;
    this._router.navigate(['/lessons/list-lesson'],{
      queryParams: {'fieldNameSort':fieldNameSort,'isIncr ementSort':isIncrementSort,'fieldNameSearch':fieldNameSearch,'valueFieldNameSearch':valueFieldNameSearch}
      
    })
    
  }

}
