import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { ProgramResponse } from 'src/app/api-clients/model/program.model';
import { RoleResponse } from 'src/app/api-clients/model/role.model';
import { ProgramClient } from 'src/app/api-clients/program.client';
import { RoleClient } from 'src/app/api-clients/role.client';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-role-details',
  templateUrl: './role-details.component.html',
  styleUrls: ['./role-details.component.scss'],
})
export class RoleDetailsComponent implements OnInit {
  public roleDetail: RoleResponse = new RoleResponse();
  public roleForm: FormGroup;
  public isAddProgram = false;
  public inputSearch = '';
  public programCurrent : ProgramResponse;
  public list_program: ProgramResponse[];
  public isDisableSubmit = false;

  public searchForm: FormGroup;
  public pages = [];
  private _pageRequest = new PageRequest(1, 5, null, true, null, null);
  public pageCurrent = 1;
  public isSearch =false;

  constructor(private _roleClient: RoleClient,
    private _programClient: ProgramClient, 
    private form: FormBuilder,
    private _toastr: ToastrService, 
    private route: ActivatedRoute,
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
            title: 'Program name',
            editable: false,
        },
        
        description: {
          title: 'Description',
          editable: false,
        },
        type: {
          title: 'Type',
          editable: false,
        },
        module: {
          title: 'Module',
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
            this._roleClient.deleteProgramIntoRole(this.roleDetail.id,event.data.id).subscribe(() => {
              this.getData();
              
              this._toastr.success('Success', 'Delete Program Into Role success!');
            })
            
          }
      });
  
    }
  
  goToAddProgram(){
    this.isAddProgram = !this.isAddProgram;
  }
 
  getData(): void{
      this.route.params.subscribe(params =>{
      let id  =  params['roleId'] ; 
      this._roleClient.getRoleDetails(id).subscribe(
      response =>{
        this.roleDetail = response.content
        this.createProfileForm();
        this.setDefaultValueForm();
      } 
    )
   })
  }

  setDefaultValueForm(){
     
    this.roleForm.patchValue({
      name: this.roleDetail.name,
      description: this.roleDetail.description,
      programs: this.roleDetail.programs
    })
  }
  createProfileForm() {
    this.roleForm = this.form.group({
      name: [''],
      description: [''],
      programs:[],
    })
  }

  changeInputSearch(event:any){
    this._pageRequest = new PageRequest(1, 5, null, true, "name", this.inputSearch);
    this._programClient.search(this._pageRequest).subscribe(
      response =>{
         this.list_program = response.content.items;
        
      }
    );

  }
  selectRole(role){
    this.inputSearch = role.name;
    this.programCurrent =role;
    this.list_program = [];
    this.isDisableSubmit =true;

  }
  addProgram(){
    if(this.isDisableSubmit){
      this._roleClient.addProgramIntoRole(this.roleDetail.id,this.programCurrent.id).subscribe(
        () =>{
          this._toastr.success("Add Program Into Role successfully","Success")
          this.getData();
        }
      )
    }

  }


}