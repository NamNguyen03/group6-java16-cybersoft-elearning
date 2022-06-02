import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CourseClient } from 'src/app/api-clients/course.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseRp, CourseUpdateInformation } from 'src/app/api-clients/model/course.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-course',
  templateUrl: './update-course.component.html',
  styleUrls: ['./update-course.component.scss']
})
export class UpdateCourseComponent implements OnInit {

  public selected = [];
  public detailsForm: FormGroup;
  public isUpdateCourse= false;

  public detailCourse: CourseRp = new CourseRp();
  pageRequet: PageRequest = new PageRequest(1, 10,
    null,
    true,
    null,
    null);
  imgCourse: string;
  categoryCourse: string;

  constructor(private courseClient: CourseClient,
    private form: FormBuilder,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private _router:Router) {
  }

  ngOnInit() {
    this.createProfileForm();
    this.getData();
  }

  getData(): void {
    this.route.queryParams.subscribe(params => {
      let id = params['courseId'];
      console.log(id)
      this.courseClient.getDetailCourse(id).subscribe(
        response => {
          this.detailCourse = response.content;
           this.setDefaultValueForm();
           this.imgCourse = response.content.img;
           this.categoryCourse = response.content.category;
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
    this.courseClient.updateCourse(this.detailCourse.id,new CourseUpdateInformation(coursename,description,category != this.categoryCourse ? category : null ,level,this.detailCourse.img,skill1,skill2,skill3,skill4,skill5)).subscribe(
      () =>{
        this.toastr.success('Success','Update course success');
      }
    )
  }

  goToCourseDetails(){
    let courseId = this.detailCourse.id
    this._router.navigate(['/courses/course-details'],{
      queryParams: { 'courseId': courseId}
  })
}


  goToUpdateCourse(){
    this.isUpdateCourse = true;
  }
  cancelUploadImg(){
    this.detailCourse.img = this.imgCourse;
  }

  changeInputImg(event: any){
    this.courseClient.updateImg(event.target.files[0]).subscribe(response => 
     this.detailCourse.img = response.content   
    );
  }

}
