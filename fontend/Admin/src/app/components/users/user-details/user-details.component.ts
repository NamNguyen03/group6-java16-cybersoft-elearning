import { Component, OnInit } from '@angular/core';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { UpdateUserRq, UserRp } from 'src/app/api-clients/model/user.model';
import { UserClient } from 'src/app/api-clients/user.client';
import Swal from 'sweetalert2';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PageService } from 'src/app/shared/service/page/page.service';
import { id } from '@swimlane/ngx-charts';
import { GroupClient } from 'src/app/api-clients/group.client';
import { HttpClient, HttpClientJsonpModule, HttpEvent } from '@angular/common/http';
import { BaseGroup } from 'src/app/api-clients/model/group.model';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {
  public user_list: UserRp[] = [];
  public profile: UserRp = new UserRp();
  public profileForm: FormGroup;
  public isAddGroup = false;

  public searchForm: FormGroup;
  public pages = [];
  private _pageRequest = new PageRequest(1, 5, null, true, null, null);
  public pageCurrent = 1;
  public isSearch = false;

  constructor(private _userClient: UserClient,
    private _router: Router,
    private form: FormBuilder,
    private _pageService: PageService,
    private _toastr: ToastrService,
    private route: ActivatedRoute,
    private _GroupClient: GroupClient,
    private http: HttpClient) {

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
      name: {
        title: 'Group name',
        editable: false,
      },

      description: {
        title: 'Description',
        editable: false,
      },


    },
  }

  ngOnInit() {
    this.getData();

  }

  onDeleteConfirm(event: any): void {
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
        this._userClient.deleteGroupIntoUser(this.profile.id, event.data.id).subscribe(() => {
          this.getData();

          this._toastr.success('Success', 'Delete Group Into User success!');
        })

      }
    });

  }
  loadData(): void {
    this._userClient.search(this._pageRequest).subscribe(
      response => {
        this.user_list = response.content.items;
        this.pages = this._pageService.getPager(response.content.pageCurrent, response.content.totalPage);
      }
    );
  }



  addGroup(event) {
    let userId = this.profile.id;
    this._router.navigate(["/users/add-group/" + userId])
  }

  getData(): void {
    this.route.params.subscribe(params => {
      let id = params['userId'];
      this._userClient.getProfile(id).subscribe(
        response => {
          this.profile = response.content
          this.createProfileForm();
          this.setDefaultValueForm();
        }
      )
    })
  }

  setDefaultValueForm() {

    this.profileForm.patchValue({
      firstName: this.profile.firstName,
      lastName: this.profile.lastName,
      email: this.profile.email,
      displayName: this.profile.displayName,
      hobbies: this.profile.hobbies,
      facebook: this.profile.facebook,
      gender: this.profile.gender,
      phone: this.profile.phone,
      groups: this.profile.groups,
    })
  }
  createProfileForm() {
    this.profileForm = this.form.group({
      firstName: [''],
      lastName: [''],
      email: [''],
      displayName: [''],
      hobbies: [''],
      facebook: [''],
      gender: [''],
      phone: [''],
      groups: [],
    })
  }
  onGroupRowSelected(event) {
    let groupId = event.data.id;

    this._router.navigate(['/groups/group-details/' + groupId]);

  }


}