import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgbDateStruct, NgbDate, NgbCalendar, NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { CourseClient } from 'src/app/api-clients/course.client';
import { CourseCreate } from 'src/app/api-clients/model/course.model';

@Component({
  selector: 'app-create-course',
  templateUrl: './create-course.component.html',
  styleUrls: ['./create-course.component.scss']
})
export class CreateCourseComponent implements OnInit {
  public generalForm: FormGroup;
  public restrictionForm: FormGroup;
  public usageForm: FormGroup;
  public model: NgbDateStruct;
  public date: { year: number, month: number };
  public modelFooter: NgbDateStruct;
  public inputImg: File;
  public imgCourse = '';

  constructor(private courseClient: CourseClient,private formBuilder: FormBuilder, private calendar: NgbCalendar,private toastr: ToastrService) {
    this.createGeneralForm();
  }

  selectToday() {
    this.model = this.calendar.getToday();
  }

  createGeneralForm() {
    this.generalForm = this.formBuilder.group({
      courseName: ['',Validators.required],
      description: ['',Validators.required],
      category: ['',Validators.required],
      level: ['',Validators.required],
      img: ['',Validators.required],
      skill1: ['',Validators.required],
      skill2: ['',Validators.required],
      skill3: ['',Validators.required],
      skill4: ['',Validators.required],
      skill5: ['',Validators.required],
    });
  }
  ngOnInit() {

  }
  saveCourse(): void{
    let coursename = this.generalForm.controls['courseName'].value;
    let description = this.generalForm.controls['description'].value;
    let category = this.generalForm.controls['category'].value;
    let level = this.generalForm.controls['level'].value;
    let img = this.imgCourse;
    let skill1 = this.generalForm.controls['skill1'].value;
    let skill2 = this.generalForm.controls['skill2'].value;
    let skill3 = this.generalForm.controls['skill3'].value;
    let skill4 = this.generalForm.controls['skill4'].value;
    let skill5 = this.generalForm.controls['skill5'].value;
    
      this.courseClient.createCourse(new CourseCreate(coursename,description,category,level,img,skill1,skill2,skill3,skill4,skill5)).subscribe(
        response =>{
          this.toastr.success('Success','Create course success');
        }
      )
   }
   changeInputImg(event: any){
     this.courseClient.updateImg(event.target.files[0]).subscribe(response => {
      this.imgCourse = response.content
     });
   }

   getImgCourse(): string {
    return this.imgCourse ? this.imgCourse : "./assets/images/course/book.jpg";
   }
}
