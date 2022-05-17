import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { ToastrService } from 'ngx-toastr';
import { LessonClient } from 'src/app/api-clients/lesson.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { LessonRp, UpdateLesson } from 'src/app/api-clients/model/lesson.model';

@Component({
  selector: 'app-lesson-info',
  templateUrl: './lesson-info.component.html',
  styleUrls: ['./lesson-info.component.scss']
})
export class LessonInfoComponent implements OnInit {

  public isUpdateInfo = false;
  public selected = [];
  public detailsForm: FormGroup;
  public infoLesson: LessonRp = new LessonRp();

  pageRequet: PageRequest = new PageRequest(1, 10,
    null,
    true,
    null,
    null);

  constructor(
    private lessonClient: LessonClient,
    private toastr: ToastrService,
    private form: FormBuilder,
    private route: ActivatedRoute,
  ) { }

  public editorConfig: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '400',
    minHeight: '400',
    maxHeight: '400',
    width: 'auto',
    minWidth: '1080',
    translate: 'yes',
    enableToolbar: true,
    showToolbar: true,
    placeholder: 'Enter content here...',
    defaultParagraphSeparator: '',
    defaultFontName: '',
    defaultFontSize: '',
    fonts: [
      { class: 'arial', name: 'Arial' },
      { class: 'times-new-roman', name: 'Times New Roman' },
      { class: 'calibri', name: 'Calibri' },
      { class: 'comic-sans-ms', name: 'Comic Sans MS' }
    ],
    customClasses: [
      {
        name: 'quote',
        class: 'quote',
      },
      {
        name: 'redText',
        class: 'redText'
      },
      {
        name: 'titleText',
        class: 'titleText',
        tag: 'h1',
      },
    ]
  };

  ngOnInit() {
    this.getData();
    this.createProfileForm();
  }

  getData(): void {
    this.route.queryParams.subscribe(params => {
      let id = params['lessonId'];
      this.lessonClient.getInfoLesson(id).subscribe(
        response => {
          this.infoLesson = response.content
          this.setDefaultValueForm();
        }
      )
    })
  }

  setDefaultValueForm(){
    this.detailsForm.patchValue({
      name: this.infoLesson.name,
      description: this.infoLesson.description,
      content: this.infoLesson.content
    })
  }

  goToUpdateInfo() {
    this.isUpdateInfo = true;
  }

  goToInfo() {
    this.isUpdateInfo = false;
  }
   
  updateInfo(){
    
    let name = this.detailsForm.controls['name'].value;
    let content = this.detailsForm.controls['content'].value;
    let description = this.detailsForm.controls['description'].value;

    let lessonupdate = new UpdateLesson(name, content, description);
    this.route.queryParams.subscribe(params => {
      let id = params['lessonId'];
      if (this.detailsForm.valid) {
       this.lessonClient.updateLesson(id,lessonupdate).subscribe(
          response => {
            this.toastr.success('Success', 'Update lesson success');
          }
        )
      }
    })
  }

  createProfileForm() {
    this.detailsForm = this.form.group({
      name: [''],
      description: [''],
      content: ['']
    })
  }
}
