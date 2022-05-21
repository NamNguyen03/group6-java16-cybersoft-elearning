import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { BaseProgram } from 'src/app/api-clients/model/program.model';
import { ProgramClient } from 'src/app/api-clients/program.client';
import { PageService } from 'src/app/shared/service/page/page.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list-program',
  templateUrl: './list-program.component.html',
  styleUrls: ['./list-program.component.scss']
})
export class ListProgramComponent implements OnInit {
  public list_program: BaseProgram[] = [];
  public searchForm: FormGroup;
  public isSearch = false;
  public pages = [];
  public pageCurrent = 1;


  pageRequest: PageRequest = new PageRequest(1,
    10,
    null,
    true,
    null,
    null);

  constructor( private form: FormBuilder,
              private toastr: ToastrService,
              private _router: Router,
              private route: ActivatedRoute,
              private programClient: ProgramClient,
              private _pageService:PageService) {
      this.searchForm = this.form.group({
        fieldNameSort:[''],
        isIncrementSort:[''],
        fieldNameSearch:[''],
        valueFieldNameSearch: ['']
      })
     }

  ngOnInit(): void {
    this.getPageDetails();
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
      },
      type: {
        title: 'Type',
        editable: true,
      },
      module: {
        title: 'Module',
        editable: true,
      }
      
    }
  
  }
  getPageDetails(): void{
   
    this.route.queryParams.subscribe(params => {
      let fieldNameSort = params['fieldNameSort'] == undefined ? null: params['fieldNameSort'];
      let isIncrementSort = params['isIncrementSort'] == (undefined||null) ? true : params['isIncrementSort'];
      let fieldNameSearch = params['fieldNameSearch'] == undefined ? '': params['fieldNameSearch'];
      let valueFieldNameSearch = params['valueFieldNameSearch'] == undefined ? '': params['valueFieldNameSearch'];
      this.pageCurrent = params['page'] == undefined ? 1 : params['page'];
      this.pageRequest = new PageRequest(this.pageCurrent, 10, fieldNameSort, isIncrementSort, fieldNameSearch, valueFieldNameSearch);
      this.loadData();

  });
  }
  loadData() {
    
      this.programClient.search(this.pageRequest).subscribe(
        response =>
        {
          this.list_program= response.content.items
          this.pages = this._pageService.getPager(response.content.pageCurrent, response.content.totalPage);
        });
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
          
          this.programClient.deleteById(event.data.id).subscribe(
            response => 
            { 
              isLoadData=true;

              this.toastr.success('Success','Delete program success')
              this.loadData();
            });
            if(!isLoadData){
              this.loadData();

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
        console.log(event.newdata)
          let groupUpdate = new BaseProgram(event.newData.name,event.newData.description,event.newData.type,event.newData.module)
         this.programClient.updateById(event.data.id,groupUpdate).subscribe(
          () => 
            { 
              isLoadData=true;
              this.toastr.success('Success','Update program success')
              this.loadData();
            });
            if(!isLoadData){
              this.loadData();

            }
                  
           }
          });
  }
  search(){
    let fieldNameSort = this.searchForm.controls['fieldNameSort'].value;
    let isIncrementSort = this.searchForm.controls['isIncrementSort'].value;
    let fieldNameSearch = this.searchForm.controls['fieldNameSearch'].value;
    let valueFieldNameSearch = this.searchForm.controls['valueFieldNameSearch'].value;
    this._router.navigate(['/program/list-program'],{
      queryParams: {'fieldNameSort':fieldNameSort,'isIncrementSort':isIncrementSort,'fieldNameSearch':fieldNameSearch,'valueFieldNameSearch':valueFieldNameSearch}

    })
   }
   clickPage(index: string): void {
    if(index == 'next'){
      this.pageCurrent++;
    }
    if(index == 'prev'){
      this.pageCurrent--;
    }
    if(index != 'prev' && index != 'next'){
      this.pageCurrent = Number(index);
    }
    this.search();
  }
}
