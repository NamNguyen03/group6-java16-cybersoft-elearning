import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { listCouponsDB } from 'src/app/shared/tables/list-coupon';

@Component({
  selector: 'app-list-coupon',
  templateUrl: './list-coupon.component.html',
  styleUrls: ['./list-coupon.component.scss']
})
export class ListCouponComponent implements OnInit {

  public digital_categories:  = [];
  public selected = [];

  constructor(  private datePipe: DatePipe) {
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
        createdDate: {
            title: 'Created Date',
            valuePrepareFunction: (createdDate) => {
                return this.datePipe.transform(
                    new Date(createdDate),
                    'dd MMM yyyy'
                );
            },
            editable: false,
        },
        createdBy: {
            title: 'Created By',
            editable: false,
        },
        lastModifiedAt: {
            title: 'Last Modified At',
            valuePrepareFunction: (lastModifiedAt) => {
                return this.datePipe.transform(
                    new Date(lastModifiedAt),
                    'dd MMM yyyy'
                );
            },
            editable: false,
        },
        lastModifiedBy: {
            title: 'Last Modified By',
            editable: false,
        },
    },
};

  ngOnInit() { }


}
