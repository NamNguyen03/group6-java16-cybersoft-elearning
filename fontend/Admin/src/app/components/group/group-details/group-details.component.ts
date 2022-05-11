import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { GroupClient } from 'src/app/api-clients/group.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { BaseGroup, GroupResponse } from 'src/app/api-clients/model/group.model';
import { RoleResponse } from 'src/app/api-clients/model/role.model';
import { RoleClient } from 'src/app/api-clients/role.client';
import { PageService } from 'src/app/shared/service/page/page.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-group-details',
  templateUrl: './group-details.component.html',
  styleUrls: ['./group-details.component.scss'],
})
export class GroupDetailsComponent implements OnInit {
  public groupDetail: GroupResponse = new GroupResponse();
  public groupForm: FormGroup;
  public isAddRole = false;
  public inputSearch = '';
  public roleCurrent : RoleResponse;
  public list_role: RoleResponse[];
  public isDisableSubmit = false;

  public searchForm: FormGroup;
  public pages = [];
  private _pageRequest = new PageRequest(1, 5, null, true, null, null);
  public pageCurrent = 1;
  public isSearch =false;

  constructor(private _groupClient: GroupClient,
    private _RoleClient: RoleClient, 
    private _router: Router,
    private form: FormBuilder,
    private _pageService: PageService,
    private _toastr: ToastrService, 
    private route: ActivatedRoute,
    private _GroupClient: GroupClient,
    ) {
    
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
            title: 'Role name',
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
            this._groupClient.deleteRoleIntoGroup(this.groupDetail.id,event.data.id).subscribe(() => {
              this.getData();
              
              this._toastr.success('Success', 'Delete Role Into Group success!');
            })
            
          }
      });
  
    }
  
  goToAddRole(){
    this.isAddRole = !this.isAddRole;
  }
 
  getData(): void{
      this.route.queryParams.subscribe(params =>{
      let id  =  params['groupId'] ; 
      this._groupClient.getGroupDetails(id).subscribe(
      response =>{
        this.groupDetail = response.content
        this.createProfileForm();
        this.setDefaultValueForm();
      } 
    )
   })
  }

  setDefaultValueForm(){
     
    this.groupForm.patchValue({
      name: this.groupDetail.name,
      description: this.groupDetail.description,
      roles: this.groupDetail.roles 
    })
  }
  createProfileForm() {
    this.groupForm = this.form.group({
      name: [''],
      description: [''],
      roles:[],
    })
  }
  onRoleRowSelected(event) {
  //   let roleId = event.data.id;
   
  //  this._router.navigate(['/roles/role-details'],{
  //    queryParams: {'roleId':roleId}})

  }
  changeInputSearch(event:any){
    this._pageRequest = new PageRequest(1, 5, null, true, "name", this.inputSearch);
    this._RoleClient.search(this._pageRequest).subscribe(
      response =>{
         this.list_role = response.content.items;
        
      }
    );

  }
  selectRole(role){
    this.inputSearch = role.name;
    this.roleCurrent =role;
    this.list_role = [];
    this.isDisableSubmit =true;

  }
  addRole(){
    if(this.isDisableSubmit){
      this._GroupClient.addRoleIntoGroup(this.groupDetail.id,this.roleCurrent.id).subscribe(
        () =>{
          this._toastr.success("Add Role Into Group successfully","Success")
          this.getData();
        }
      )
    }

  }


}