import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CourseClient } from 'src/app/api-clients/course.client';
import { LessonClient } from 'src/app/api-clients/lesson.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseRp } from 'src/app/api-clients/model/course.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-course-details',
  templateUrl: './course-details.component.html',
  styleUrls: ['./course-details.component.scss']
})
export class CourseDetailsComponent implements OnInit {

  public selected = [];
  public detailsForm: FormGroup;

  public detailCourse: CourseRp = new CourseRp();
  public isSearch = false;
  pageRequet: PageRequest = new PageRequest(1, 10,
    null,
    true,
    null,
    null);

  constructor(private courseClient: CourseClient,
    private lessonClient: LessonClient,
    private form: FormBuilder,
    private route: ActivatedRoute,
    private toastr: ToastrService,) {
  }

  public settings = {
    pager: {
      display: true,
      perPage: 10,
    },
    delete: {
      confirmDelete: true,
    },
    actions: {
      custom: false,
      edit: false,
      delete: true,
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
    this.getData();
  }

  getData(): void {
    this.route.queryParams.subscribe(params => {
      let id = params['courseId'];
      this.courseClient.getDetailCourse(id).subscribe(
        response => {
          console.log(response)
          this.detailCourse = response.content
          // this.createProfileForm();
          // this.setDefaultValueForm();
        }
      )
    })
  }
  createProfileForm() {
    this.detailsForm.patchValue({
      coursename: this.detailCourse.courseName,
      coursetime: this.detailCourse.courseTime,
      description: this.detailCourse.description
    })
  }
  setDefaultValueForm() {
    this.detailsForm = this.form.group({
      coursename: [''],
      coursetime: [''],
      description: ['']
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
          this.lessonClient.deleteLesson(event.data.id).subscribe(() => {
            this.getData();
            isLoadData = true;
            this.toastr.success('Success', 'Delete Lesson success!');
          })

        }
    });
  }

}
