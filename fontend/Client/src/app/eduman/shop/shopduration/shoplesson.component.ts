import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HomepageService } from 'src/app/share/homepage/homepage.service';

@Component({
  selector: 'app-shopduration',
  templateUrl: './shopduration.component.html',
  styleUrls: ['./shopduration.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ShopdurationComponent implements OnInit {
  shopDurationActive:boolean=false;
  shopDuration(){
    if(this.shopDurationActive==false){
      this.shopDurationActive=true;
    }
    else {
      this.shopDurationActive=false;
    }
  }

  constructor(private _homepageService: HomepageService) { }

  ngOnInit(): void {}

  changeTimeLesson(event: any ){
    if(event.target.value == 0){
      this._homepageService.setTime(1,100);
    }
    if(event.target.value == 1){
      this._homepageService.setTime(1,6);
    }
    if(event.target.value == 5){
      this._homepageService.setTime(5,11);
    }
    if(event.target.value == 11){
      this._homepageService.setTime(10,15);
    }
  }
}
