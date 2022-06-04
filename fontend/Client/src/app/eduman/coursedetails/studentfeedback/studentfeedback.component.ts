import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {  ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { FeedBackClient } from 'src/app/api-clients/feedback.client';
import { LessonClient } from 'src/app/api-clients/lesson.client';
import { RatingCreate, RatingResponse } from 'src/app/api-clients/model/feedback.model';
import { LessonDetailsResponseClientDTO } from 'src/app/api-clients/model/lesson.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-studentfeedback',
  templateUrl: './studentfeedback.component.html',
  styleUrls: ['./studentfeedback.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class StudentfeedbackComponent implements OnInit {
  private idLesson ='';
  public myRating = 0;

  constructor( private feedBackClient: FeedBackClient,
    private route: ActivatedRoute,
    private _toastr: ToastrService,
    private lessonClient: LessonClient, ) { }
    public lessonDetails: LessonDetailsResponseClientDTO = new LessonDetailsResponseClientDTO();


  ngOnInit(): void {
    this.loadData();    
    
  }
  loadData(){
    this.route.params.subscribe((params) => {
      this.idLesson = params["id"];
      this.lessonClient.getLessonDetails(this.idLesson).subscribe(
        response => {
        this.lessonDetails = response.content;
        console.log(this.lessonDetails);
       })
     this.findMyRating();
  })
 
  }

  findMyRating(){
      this.feedBackClient.myRating(this.idLesson).subscribe(
        response => {

          this.myRating = response.content ? response.content.value : 0;
        }
        ) ;
    }
  

  rating(number: number): void{
    if (this.myRating==0) {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, Reviews it!',
    }).then((results) => {
      if (results.isConfirmed) {
       this.feedBackClient.writeRating(new RatingCreate(number,this.idLesson)).subscribe(
          () => {
           this._toastr.success('Success','Reviews Successfully')
           this.loadData();
          }
        )
       }
      })}

  
}
}
