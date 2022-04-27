import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgbDateStruct, NgbDate, NgbCalendar, NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { response } from 'express';
import { CourseClient } from 'src/app/api-clients/course.client';
import { PageRequestModel } from 'src/app/api-clients/model/common.model';
import { CourseCreate } from 'src/app/api-clients/model/course.model';

@Component({
  selector: 'app-create-coupon',
  templateUrl: './create-coupon.component.html',
  styleUrls: ['./create-coupon.component.scss']
})
export class CreateCouponComponent implements OnInit {
  public generalForm: FormGroup;
  public restrictionForm: FormGroup;
  public usageForm: FormGroup;
  public model: NgbDateStruct;
  public date: { year: number, month: number };
  public modelFooter: NgbDateStruct;

  constructor(private courseClient: CourseClient,private formBuilder: FormBuilder, private calendar: NgbCalendar) {
    this.createGeneralForm();
  }

  selectToday() {
    this.model = this.calendar.getToday();
  }

  createGeneralForm() {
    this.generalForm = this.formBuilder.group({
      courseName: ['',Validators.required],
      courseTime: ['',Validators.required],
      description: ['',Validators.required],
    });
  }
  ngOnInit() {

  }
  saveCourse(): void{
    let coursename = this.generalForm.controls['courseName'].value;
    let coursetime = this.generalForm.controls['courseTime'].value;
    let desc = this.generalForm.controls['description'].value;

     if(this.generalForm.valid){
      this.courseClient.createCourse(new CourseCreate(coursename,coursetime,desc)).subscribe(
        response =>{
          console.log(response);
          this.createGeneralForm();
        }
      )
    }

  }

}
