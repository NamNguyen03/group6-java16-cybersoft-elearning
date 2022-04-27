import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgbDateStruct, NgbDate, NgbCalendar, NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { CourseClient } from 'src/app/api-clients/course.client';
import { CourseCreate } from 'src/app/api-clients/model/course.model';
import Swal from 'sweetalert2';

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

  constructor(private courseClient: CourseClient,private formBuilder: FormBuilder, private calendar: NgbCalendar,private toastr: ToastrService) {
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
          this.toastr.success('Success','Create role success');
        }
      )
    }

  }

}
