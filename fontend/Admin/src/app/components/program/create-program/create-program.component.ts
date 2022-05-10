import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { BaseProgram } from 'src/app/api-clients/model/program.model';
import { ProgramClient } from 'src/app/api-clients/program.client';

@Component({
  selector: 'app-create-program',
  templateUrl: './create-program.component.html',
  styleUrls: ['./create-program.component.scss']
})
export class CreateProgramComponent implements OnInit {
  public generalForm: FormGroup;
  public seoForm: FormGroup;
  
  constructor(private formBuilder: FormBuilder,
    private programClient: ProgramClient,
    private toastr: ToastrService) { 
      this.createProgramForm();
    }

  ngOnInit(): void {
  }

  createProgramForm() {
    this.generalForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(3)]],
      type: ['' , Validators.required],
      module: ['' , Validators.required],
    });
  }

  createProgram(): void{
    let name = this.generalForm.controls['name'].value;
    let description = this.generalForm.controls['description'].value;
    let type = this.generalForm.controls['type'].value;
    let module = this.generalForm.controls['module'].value;
    this.programClient.createProgram(new BaseProgram(name,description,type,module)).subscribe
    (response =>{
      this.toastr.success('Success','Create Program success')
    })
    }
}
