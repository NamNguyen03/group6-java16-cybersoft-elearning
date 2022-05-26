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
  selector: 'app-list-comment-details',
  templateUrl: './list-comment-details.component.html',
  styleUrls: ['./list-comment-details.component.scss']
})
export class ListCommentDetailsComponent implements OnInit {

  public searchForm: FormGroup;
  public pages = [];
  private _pageRequest = new PageRequest(0, 10, null, true, null, null);
  public pageCurrent = 1;
  public isSearch =false;
  public statusComments: StatusCommentRp[] = [];

  constructor(
    private statusCommentClient: StatusCommentClient, 
    private _router: Router,
    private form: FormBuilder,
    private _pageService: PageService,
    private _toastr: ToastrService, 
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.createFormSearch();
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
        confirmDelete: true,
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
        // nameCourses: {
        //   title:'Course name',
        //   editable: false,
        //   valuePrepareFunction: (course: CourseStatusComment) => {
        //     return course.courseName;
        //   }  
        // },
        // username: {
        //     title: 'Display name',
        //     editable: false,
        //     valuePrepareFunction: (user: UserStatusComment) => {
        //       return user.displayName;
        //     }  
        // },
        // displayName: {
        //     title: 'Email',
        //     type: 'email',
        //     editable: false,
        //     valuePrepareFunction: (user: UserStatusComment) => {
        //       return user.displayName;
        //     }  
        // },
        // status: {
        //     title: 'Status',
        //     editor: {
        //         type: 'list',
        //         config: {
        //             selectText: 'Select',
        //             list: [
        //                 { value: 'PRIVATE', title: 'PRIVATE' },
        //                 { value: 'PUBLIC', title: 'PUBLIC' },
        //                 { value: 'BLOCKED', title: 'BLOCKED' }
        //             ],
        //         },
        //     },
        // }
    },
  };

  search(){
    let fieldNameSort = this.searchForm.controls['fieldNameSort'].value;
    let isIncrementSort = this.searchForm.controls['isIncrementSort'].value;
    let fieldNameSearch = this.searchForm.controls['fieldNameSearch'].value;
    let valueFieldNameSearch = this.searchForm.controls['valueFieldNameSearch'].value;
    
    this._router.navigate(['/users/list-user'],{
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
  //   let userId = event.data.id;
  //   console.log(userId)
   
  //  this._router.navigate(['/users/user-details'],{
  //    queryParams: {'userId':userId}})

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
      // let id = event.newData.id;
      // let email = event.newData.email;
      // let department = event.newData.department;
      // let major = event.newData.major;
      // let status = event.newData.status;
      // let isLoadData = false;

      // this._userClient.updateUser(id, new UpdateUserRq(email, status, major, department)).subscribe(
      //   () => {
      //     this.loadData();
      //     isLoadData = true;
      //     this._toastr.success('Success', 'Update User Successfully')
      //   }
      // )
      // if(!isLoadData) {
      //   this.loadData();
      // }
    }});
  }
}
