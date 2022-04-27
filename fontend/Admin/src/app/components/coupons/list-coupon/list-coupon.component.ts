import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { response } from 'express';
import { ToastrService } from 'ngx-toastr';
import { CourseClient } from 'src/app/api-clients/course.client';
import { PageRequestModel, SearchRequest } from 'src/app/api-clients/model/common.model';
import { listCouponsDB } from 'src/app/shared/tables/list-coupon';
import Swal from 'sweetalert2';
import { ActivatedRoute } from '@angular/router';
import { CourseUpdateInformation } from 'src/app/api-clients/model/course.model';

@Component({
  selector: 'app-list-coupon',
  templateUrl: './list-coupon.component.html',
  styleUrls: ['./list-coupon.component.scss']
})
export class ListCouponComponent implements OnInit {

  public course_list = [];
  public selected = [];
  rq: SearchRequest = {};
  private _pageRequest = new PageRequestModel(0, 2, null, true, null, null);

  constructor( private courseClient: CourseClient,
    private toastr: ToastrService,private route: ActivatedRoute) {
  }

  onSelect({ selected }) {
    this.selected.splice(0, this.selected.length);
    this.selected.push(...selected);
  }

  public settings = {
    pager: {
        display: true,
        perPage: 5,
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

  ngOnInit() {
    this.loadData();
  }

   loadData(){
     this.courseClient.SearchRequest(this.rq).subscribe(
      response =>{
        console.log(response);
        this.course_list = response.content.items;
      }
      
    );    
  }

  onDeleteConfirm(event) {
    console.log('hello');
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
          
          if(!isLoadData) {
            this.loadData();
            this.toastr.success('Success', 'AAAAAAAAAAAAAAAAAAA');
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
          console.log('save', event);
          event.confirm.resolve(event.newData);
          console.log(event.newData.id);
          let courseUpdate = new CourseUpdateInformation(event.newData.courseName,event.newData.courseTime,event.newData.description);
          this.courseClient.updateCourse(event.data.id,courseUpdate).subscribe(() => {
            
            this.loadData();
            this.toastr.success('Success', 'Update Course success!');
          })
          if(!isLoadData) {
            this.loadData();
          }
        }
    });

  }

}
