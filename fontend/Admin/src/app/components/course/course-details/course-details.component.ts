import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CourseClient } from 'src/app/api-clients/course.client';
import { LessonClient } from 'src/app/api-clients/lesson.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseRp, CourseUpdateInformation } from 'src/app/api-clients/model/course.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-course-details',
  templateUrl: './course-details.component.html',
  styleUrls: ['./course-details.component.scss']
})
export class CourseDetailsComponent implements OnInit {

  public selected = [];
  public detailsForm: FormGroup;
  public isUpdateCourse= false;
  public isActions = false;
  public isUpdateImg = false;

  public detailCourse: CourseRp = new CourseRp();
  public isSearch = false;
  pageRequet: PageRequest = new PageRequest(1, 10,
    null,
    true,
    null,
    null);
  imgCourse: string;

  constructor(private courseClient: CourseClient,
    private lessonClient: LessonClient,
    private form: FormBuilder,
    private route: ActivatedRoute,
    private _router: Router,
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
           this.createProfileForm();
           this.setDefaultValueForm();
           this.imgCourse = response.content.img;
        }
      )
    })
  }
  setDefaultValueForm() {
    this.detailsForm.patchValue({
      img:this.detailCourse.img,
      courseName: this.detailCourse.courseName, 
      description: this.detailCourse.description,
      category:this.detailCourse.category,
      level:this.detailCourse.level,
      starAvg:this.detailCourse.starAvg,
      totalStar:this.detailCourse.totalStar,
      totalRating:this.detailCourse.totalRating,
      skill1:this.detailCourse.skill1,
      skill2:this.detailCourse.skill2,
      skill3:this.detailCourse.skill3,
      skill4:this.detailCourse.skill4,
      skill5:this.detailCourse.skill5,
      lessons:this.detailCourse.lessons
    })
  }
  
  createProfileForm() {
    this.detailsForm = this.form.group({
      img:[''],
      courseName: [''],
      description: [''],
      category:[''],
      level:[''],
      starAvg:[''],
      totalStar:[''],
      totalRating:[''],
      skill1:[''],
      skill2:[''],
      skill3:[''],
      skill4:[''],
      skill5:[''],
      lesson:[],
      
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

  createLesson(){
    this._router.navigate(["/lessons/create-lesson"],{ queryParams: {'courseId':this.detailCourse.id}})
  }

  onUserRowSelected(event) {
    let lessonId = event.data.id;
    this._router.navigate(['courses/lesson-info'], {
      queryParams: { 'lessonId': lessonId }
    })
  }
  updateCourse(){
    let coursename = this.detailsForm.controls['courseName'].value;
    let description = this.detailsForm.controls['description'].value;
    let category = this.detailsForm.controls['category'].value;
    let level = this.detailsForm.controls['level'].value;
    let skill1 = this.detailsForm.controls['skill1'].value;
    let skill2 = this.detailsForm.controls['skill2'].value;
    let skill3 = this.detailsForm.controls['skill3'].value;
    let skill4 = this.detailsForm.controls['skill4'].value;
    let skill5 = this.detailsForm.controls['skill5'].value;
    this.courseClient.updateCourse(this.detailCourse.id,new CourseUpdateInformation(coursename,description,category,level,this.detailCourse.img,skill1,skill2,skill3,skill4,skill5)).subscribe(
      response =>{
        this.toastr.success('Success','Update course success');
      }
    )
  }

  goToUpdateCourse(){
    let courseId = this.detailCourse.id
    this._router.navigate(['/courses/update-course'],{
      queryParams: { 'courseId': courseId}
  })
  }
  toggleAction(){
    this.isActions = !this.isActions;
    if(!this.isActions){
      this.isUpdateImg = true;
    }
  }
  changeInputImg(event: any){
    this.courseClient.updateImg(event.target.files[0]).subscribe(response => 
     this.detailCourse.img = response.content   
    );
  }

}
