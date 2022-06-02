import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CourseClient } from 'src/app/api-clients/course.client';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseUpdateInformation } from 'src/app/api-clients/model/course.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { PageService } from 'src/app/shared/service/page/page.service';

@Component({
  selector: 'app-list-course',
  templateUrl: './list-course.component.html',
  styleUrls: ['./list-course.component.scss']
})
export class ListCourseComponent implements OnInit {
  public isSearch = false;
  public course_list = [];
  public selected = [];
  public searchForm: FormGroup;
  public pages = [];
  private pageRequest: PageRequest = new PageRequest(0, 10, null, true, null, null);
  public pageCurrent = 1;

  constructor(private courseClient: CourseClient,
    private form: FormBuilder,
    private toastr: ToastrService,
    private _router: Router,
    private _pageService: PageService,
    private route: ActivatedRoute) {
    this.searchForm = this.form.group({
      fieldNameSort: [''],
      isIncrementSort: [''],
      fieldNameSearch: [''],
      valueFieldNameSearch: ['']
    })
  }

  onSelect({ selected }) {
    this.selected.splice(0, this.selected.length);
    this.selected.push(...selected);
  }

  clickSearch(){
    this.pageCurrent = 1;
    this.search();
  }

  toggleSearch() {
    this.isSearch = !this.isSearch;
  }
  public settings = {
    pager: {
      display: true,
      perPage: 10,
    },
    delete: {
      deleteButtonContent: 'Delete',
      confirmDelete: true
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
      courseName: {
        title: 'Course Name',
        editable: true,
      },
      description: {
        title: 'Description',
        editable: true,
      },
      courseTime: {
        title: 'Lesson',
        editable: true,
      },

    },
  };

  ngOnInit(): void {
    this.getPageDetails();
  }
  getPageDetails(): void {

    this.route.queryParams.subscribe(params => {
      let fieldNameSort = params['fieldNameSort'] == undefined ? null : params['fieldNameSort'];
      let isIncrementSort = params['isIncrementSort'] == (undefined || null) ? true : params['isIncrementSort'];
      let fieldNameSearch = params['fieldNameSearch'] == undefined ? '' : params['fieldNameSearch'];
      let valueFieldNameSearch = params['valueFieldNameSearch'] == undefined ? '' : params['valueFieldNameSearch'];
      this.pageCurrent = params['page'] == undefined ? 1 : params['page'];
      this.pageRequest = new PageRequest(this.pageCurrent, 10, fieldNameSort, isIncrementSort, fieldNameSearch, valueFieldNameSearch);
      this.loadData();

    });
  }

  loadData() {
  
    this.courseClient.searchRequest(this.pageRequest).subscribe(
      response => {
        this.course_list = response.content.items;
        this.pages = this._pageService.getPager(response.content.pageCurrent, response.content.totalPage);

      }

    );
  }

  onDeleteConfirm(event) {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        let isLoadData = false;
        this.courseClient.deleteCourse(event.data.id).subscribe(() => {
          this.loadData();
          isLoadData = true;
          this.toastr.success('Success', 'Delete Course success!');
        })

        if (!isLoadData) {
          this.loadData();
          this.toastr.success('Success', 'Delete Course fails!');
        }
      }
    });
  }
  clickPage(index: string): void {
    if (index == 'next') {
      this.pageCurrent++;
    }
    if (index == 'prev') {
      this.pageCurrent--;
    }
    if (index != 'prev' && index != 'next') {
      this.pageCurrent = Number(index);
    }
    this.search();
  }

  onSaveConfirm(event) {
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
        let isLoadData = false;
        event.confirm.resolve(event.newData);
        let courseUpdate = new CourseUpdateInformation(event.newData.courseName, event.newData.description,
          event.newDate.category, event.newDate.level,
          event.newDate.img, event.newDate.skill1, event.newDate.skill2, event.newDate.skill3, event.newDate.skill4, event.newDate.skill5);
        this.courseClient.updateCourse(event.data.id, courseUpdate).subscribe(() => {
          this.loadData();
          isLoadData = true;
          this.toastr.success('Success', 'Update Course success!');
        })
        if (!isLoadData) {
          this.loadData();
        }
      }
    });

  }

  search() {
    let fieldNameSort = this.searchForm.controls['fieldNameSort'].value == "NONE" ? null :this.searchForm.controls['fieldNameSort'].value;
    let isIncrementSort = this.searchForm.controls['isIncrementSort'].value;
    let fieldNameSearch = this.searchForm.controls['fieldNameSearch'].value== "NONE" ? null :this.searchForm.controls['fieldNameSearch'].value;
    let valueFieldNameSearch = this.searchForm.controls['valueFieldNameSearch'].value;

    this._router.navigate(['/courses/list-course'], {
      queryParams: {
        'page': this.pageCurrent,
        'fieldNameSort': fieldNameSort,
        'isIncrementSort': isIncrementSort,
        'fieldNameSearch': fieldNameSearch,
        'valueFieldNameSearch': valueFieldNameSearch
      }

    })
  }


  onUserRowSelected(event) {
    let courseId = event.data.id;
    this._router.navigate(['/courses/course-details'], {
      queryParams: { 'courseId': courseId }
    })

  }

}
