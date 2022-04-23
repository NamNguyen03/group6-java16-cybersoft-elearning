import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { response } from 'express';
import { CourseClient } from 'src/app/api-clients/course.client';
import { PageRequestModel, SearchRequest } from 'src/app/api-clients/model/common.model';
import { listCouponsDB } from 'src/app/shared/tables/list-coupon';

@Component({
  selector: 'app-list-coupon',
  templateUrl: './list-coupon.component.html',
  styleUrls: ['./list-coupon.component.scss']
})
export class ListCouponComponent implements OnInit {

  public course_list = [];
  public selected = [];
  rq: SearchRequest = {};

  constructor( private courseClient: CourseClient) {
  //this.digital_categories = listCouponsDB.list_coupons; 
  
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
        confirmDelete: true,
    },
    edit: {
        confirmSave: true,
    },
    actions: {
        custom: false,
        delete: false,
        add: false,
    },
    columns: {
        courseName: {
            title: 'Course name',
            editable: false,
        },
        courseTime: {
            title: 'Course Time',
            editable: false,
        },
        description: {
            title: 'description',
            editable: false,
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

}
