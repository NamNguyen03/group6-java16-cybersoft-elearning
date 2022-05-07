import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CourseClient } from 'src/app/api-clients/course.client';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseUpdateInformation } from 'src/app/api-clients/model/course.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PageRequest } from 'src/app/api-clients/model/common.model';

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
  pageRequet: PageRequest = new PageRequest(1, 10,
    null,
    true,
    null,
    null);

  constructor(private courseClient: CourseClient,
    private form: FormBuilder,
    private toastr: ToastrService,
    private _router: Router,
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
      courseTime: {
        title: 'Course Time',
        editable: true,
      },
      description: {
        title: 'Description',
        editable: true,
      },

    },
  };

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.route.queryParams.subscribe(params => {
      let fieldNameSort = params['fieldNameSort'] == undefined ? null : params['fieldNameSort'];
      let isIncrementSort = params['isIncrementSort'] == (undefined || null) ? true : params['isIncrementSort'];
      let fieldNameSearch = params['fieldNameSearch'] == undefined ? '' : params['fieldNameSearch'];
      let valueFieldNameSearch = params['valueFieldNameSearch'] == undefined ? '' : params['valueFieldNameSearch'];

      this.pageRequet = new PageRequest(1, 10, fieldNameSort, isIncrementSort, fieldNameSearch, valueFieldNameSearch)
      console.log(this.pageRequet);
      this.courseClient.searchRequest(this.pageRequet).subscribe(
        response => {
          this.course_list = response.content.items;
        }

      );
    })


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
        let courseUpdate = new CourseUpdateInformation(event.newData.courseName, event.newData.courseTime, event.newData.description);
        this.courseClient.updateCourse(event.data.id, courseUpdate).subscribe(() => {

          this.loadData();
          this.toastr.success('Success', 'Update Course success!');
        })
        if (!isLoadData) {
          this.loadData();
        }
      }
    });

  }


  search() {
    let fieldNameSort = this.searchForm.controls['fieldNameSort'].value;
    let isIncrementSort = this.searchForm.controls['isIncrementSort'].value;
    let fieldNameSearch = this.searchForm.controls['fieldNameSearch'].value;
    let valueFieldNameSearch = this.searchForm.controls['valueFieldNameSearch'].value;
    this._router.navigate(['/courses/list-course'], {
      queryParams: { 'fieldNameSort': fieldNameSort, 'isIncr ementSort': isIncrementSort, 'fieldNameSearch': fieldNameSearch, 'valueFieldNameSearch': valueFieldNameSearch }

    })

  }

  onUserRowSelected(event) {
    let courseId = event.data.id;

    this._router.navigate(['/courses/course-details'], {
      queryParams: { 'courseId': courseId }
    })

  }

}
