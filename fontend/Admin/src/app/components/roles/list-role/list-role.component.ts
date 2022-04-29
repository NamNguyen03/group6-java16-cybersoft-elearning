
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { BaseRole } from 'src/app/api-clients/model/role.model';
import { RoleClient } from 'src/app/api-clients/role.client';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list-role',
  templateUrl: './list-role.component.html',
  styleUrls: ['./list-role.component.scss'],
})
export class ListRoleComponent implements OnInit {
  public list_role: BaseRole[] = [];
  public searchForm: FormGroup;
  public isSearch = false;

  pageRequest: PageRequest = new PageRequest(1,
    10,
    null,
    true,
    null,
    null);

  constructor(private roleClient: RoleClient,
              private form: FormBuilder,
              private toastr: ToastrService,
              private _router: Router,
              private route: ActivatedRoute) {

    this.searchForm = this.form.group({
      fieldNameSort:[''],
      isIncrementSort:[''],
      fieldNameSearch:[''],
      valueFieldNameSearch: ['']
    })
  }
  ngOnInit(): void {
    
    this.loadData();
  }

  toggleSearch(){
    this.isSearch=!this.isSearch;
  }

  public settings = {
    pager: {
        display: true,
        perPage: 10,
    },
    delete: {
        confirmDelete: true,
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
      name: {
        title: 'Name',
        editable: true,
      },
      description: {
        title: 'Description',
        editable: true,
      }

    }
  
  }

  loadData() {
    this.route.queryParams.subscribe(params =>{
      let fieldNameSort = params['fieldNameSort'] == undefined ? null: params['fieldNameSort'];
      let isIncrementSort = params['isIncrementSort'] == (undefined||null) ? true : params['isIncrementSort'];
      let fieldNameSearch = params['fieldNameSearch'] == undefined ? '': params['fieldNameSearch'];
      let valueFieldNameSearch = params['valueFieldNameSearch'] == undefined ? '': params['valueFieldNameSearch'];

      this.pageRequest = new PageRequest(1,10,fieldNameSort,isIncrementSort,fieldNameSearch,valueFieldNameSearch)
      this.roleClient.search(this.pageRequest).subscribe(
        response =>
        {
          this.list_role= response.content.items
        });
    }) 


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
          this.roleClient.deleteById(event.data.id).subscribe(
            () => { 
              isLoadData=true;

              this.toastr.success('Success','Delete role success')
              this.loadData();
            });
            if(!isLoadData){
              this.loadData();
            }}});
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
        console.log(event.newdata)
          let roleUpdate = new BaseRole(event.newData.name,event.newData.description)
         this.roleClient.updateById(event.data.id,roleUpdate).subscribe(
          () =>{ 
              isLoadData=true;
              this.toastr.success('Success','Update role success')
              this.loadData();
            });
            if(!isLoadData){
              this.loadData();
            }}});
  }

  search(){
    let fieldNameSort = this.searchForm.controls['fieldNameSort'].value;
    let isIncrementSort = this.searchForm.controls['isIncrementSort'].value;
    let fieldNameSearch = this.searchForm.controls['fieldNameSearch'].value;
    let valueFieldNameSearch = this.searchForm.controls['valueFieldNameSearch'].value;

    this._router.navigate(['/roles/list-role'],{
      queryParams: {'fieldNameSort':fieldNameSort,'isIncrementSort':isIncrementSort,'fieldNameSearch':fieldNameSearch,'valueFieldNameSearch':valueFieldNameSearch}
    })}
  
}