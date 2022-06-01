import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HomepageService } from 'src/app/share/homepage/homepage.service';

@Component({
  selector: 'app-shoprating',
  templateUrl: './shoprating.component.html',
  styleUrls: ['./shoprating.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ShopratingComponent implements OnInit {
  shopRatingActive:boolean=false;
  shopRating(){
    if(this.shopRatingActive==false){
      this.shopRatingActive=true;
    }
    else {
      this.shopRatingActive=false;
    }
  }
  constructor(private _homepageService: HomepageService) { }

  ngOnInit(): void {}

  changeRating(event: any ){
    this._homepageService.setRating(event.target.value);
  }
}
