import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseStatusComment, StatusCommentRp, UserStatusComment } from 'src/app/api-clients/model/status-comment.model';
import { StatusCommentClient } from 'src/app/api-clients/status-comment.client';
import { PageService } from 'src/app/shared/service/page/page.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list-status-comment',
  templateUrl: './list-status-comment.component.html',
  styleUrls: ['./list-status-comment.component.scss']
})
export class ListStatusCommentComponent implements OnInit {

  public searchForm: FormGroup;
  public pages = [];
  private _pageRequest = new PageRequest(0, 10, null, true, null, null);
  public pageCurrent = 1;
  public isSearch =false;
  public statusComments: StatusCommentRp[] = [];

  constructor(
    private _statusCommentClient: StatusCommentClient, 
    private _router: Router,
    private form: FormBuilder,
    private _pageService: PageService,
    private _toastr: ToastrService, 
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.createFormSearch();
    this.getPageDetails();
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

  loadData(): void{
    this._statusCommentClient.search(this._pageRequest).subscribe(
      response =>{
        this.statusComments = response.content.items;
        this.pages = this._pageService.getPager(response.content.pageCurrent, response.content.totalPage);
      }
    );
  }

  private createFormSearch() {
    this.searchForm = this.form.group({
      fieldNameSort:[''],
      isIncrementSort:[''],
      fieldNameSearch:[''],
      valueFieldNameSearch: ['']
    })
  }

  toggleSearch(){
    this.isSearch=!this.isSearch;
  }

  public settings = {
    pager: {
        display: true,
        perPage: 10,
    },
    delete: {
        confirmDelete: false,
    },
    edit: {
      confirmSave: true,
    },
    actions: {
        custom: false,
        delete: true,
        add: false,
    },
    columns: {
        course: {
          title:'Course name',
          editable: false,
          valuePrepareFunction: (course: CourseStatusComment) => {
            return course.courseName;
          }  
        },
        user: {
            title: 'Display name',
            editable: false,
            valuePrepareFunction: (user: UserStatusComment) => {
              return user.displayName;
            }  
        },
        status: {
            title: 'Status',
            editor: {
                type: 'list',
                config: {
                    selectText: 'Select',
                    list: [
                        { value: 'PUBLIC', title: 'PUBLIC' },
                        { value: 'PRIVATE', title: 'PRIVATE' },
                        { value: 'BLOCKED', title: 'BLOCKED' }
                    ],
                },
            },
        }
    },
  };

  search(){
    let fieldNameSort = this.searchForm.controls['fieldNameSort'].value;
    let isIncrementSort = this.searchForm.controls['isIncrementSort'].value;
    let fieldNameSearch = this.searchForm.controls['fieldNameSearch'].value;
    let valueFieldNameSearch = this.searchForm.controls['valueFieldNameSearch'].value;
    
    this._router.navigate(['/comments/list-status-comment'],{
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


  onUserRowSelected(event) {
    let id = event.data.id;
    this._router.navigate(['/comments/list-comment-details/' + id]);

 }


 onSaveConfirm(event: any): void {
    
  Swal.fire({
    title: 'Are you sure?',
    text: "You won't be able to revert this!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes, update it!',
  }).then((result) => {
    if (result.isConfirmed) {
      let id = event.data.id;
      let status = event.newData.status;
      let isLoadData = false;
      this._statusCommentClient.update(id, status).subscribe(
        () =>{
          isLoadData=true;
          this._toastr.success('Update status successfully', 'Success');
          this.loadData();
        }
      )
      if(!isLoadData){
        this.loadData();

      }
    }});
  }
}
