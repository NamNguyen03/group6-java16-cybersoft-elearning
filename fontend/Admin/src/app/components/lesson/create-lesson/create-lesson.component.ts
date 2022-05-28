import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { ToastrService } from 'ngx-toastr';
import { LessonClient } from 'src/app/api-clients/lesson.client';
import { LessonCreate, LessonRp } from 'src/app/api-clients/model/lesson.model';
import { content } from 'src/app/shared/routes/content-routes';

@Component({
  selector: 'app-create-lesson',
  templateUrl: './create-lesson.component.html',
  styleUrls: ['./create-lesson.component.scss']
})
export class CreateLessonComponent implements OnInit {
  [x: string]: any;
  public accountForm: FormGroup;
  public detailCourse: LessonRp = new LessonRp();

  constructor(private formBuilder: FormBuilder, private lesonsClient: LessonClient,private route: ActivatedRoute,private toastr: ToastrService) {
    this.createAccountForm();
  }

  createAccountForm() {
    this.accountForm = this.formBuilder.group({
      name: ['', Validators.required],
      content: ['', Validators.required],
      description: ['', Validators.required],

    })
  }
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

  ngOnInit() { }

  toggleAction(): void {
    this.isActions = !this.isActions;
    if(!this.isActions){
      this.isUpdateImg = false;
    }
   }

   cancelUploadImg(): void {
    this.isUpdateImg = false;
  }


  goToUpdateImg(): void {
    this.isUpdateAvatar = true;
  }

  saveLesson(): void {
    let name = this.accountForm.controls['name'].value;
    let content = this.accountForm.controls['content'].value;
    let description = this.accountForm.controls['description'].value;
    
    this.route.queryParams.subscribe(params => {
      let id = params['courseId'];
      console.log(id)
      if (this.accountForm.valid) {
        this.lesonsClient.createLesson(new LessonCreate(name, content, description, id)).subscribe(       
          ()=> {
            this.toastr.success('Success', 'Create lesson success');
          }
        )
      }
    })

  }

  changeInputAvatar(event: any): void{
    this.lesonsClient.uploadImg(event.target.files[0]).subscribe(
      response => 
        this.accountForm.controls['content'].setValue(this.accountForm.controls['content'].value + '<br/> <img src="' + response.content + '" /> <br/> ')
      
    )
  }

}
