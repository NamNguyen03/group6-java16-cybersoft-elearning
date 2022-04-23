import { Component, OnInit } from '@angular/core';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { UserRp } from 'src/app/api-clients/model/user.model';
import { UserClient } from 'src/app/api-clients/user.client';
import Swal from 'sweetalert2';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.scss']
})
export class ListUserComponent implements OnInit {
  public user_list: UserRp[] = [];

  private _pageRequest = new PageRequest(0, 10, null, true, null, null);

  constructor(private _userClient: UserClient, private _toastr: ToastrService) {
    
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
        delete: true,
        add: false,
    },
    columns: {
        username: {
            title: 'User name',
            editable: false,
        },
        email: {
            title: 'Email',
            type: 'email',
            editable: true,
        },
        firstName: {
          title: 'First Name',
          editable: true,
        },
        lastName: {
            title: 'Last Name',
            editable: true,
        },
        status: {
            title: 'Status',
            editor: {
                type: 'list',
                config: {
                    selectText: 'Select',
                    list: [
                        { value: 'ACTIVE', title: 'ACTIVE' },
                        { value: 'TEMPORARY_BLOCKED', title: 'TEMPORARY_BLOCKED' },
                        { value: 'PERMANENT_BLOCKED', title: 'PERMANENT_BLOCKED' }
                    ],
                },
            },
        }
    },
};

  ngOnInit() {
    this.loadData();
  }

  loadData(): void{
    this._userClient.search(this._pageRequest).subscribe(
      response =>{
        this.user_list = response.content.items;
      }
    );
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
          this._userClient.delete(event.data.id).subscribe(() => {
            this.loadData();
            isLoadData = true;
            this._toastr.success('Success', 'Delete User success!');
          })
          
          if(!isLoadData) {
            this.loadData();
          }
        }
    });

  }
}

