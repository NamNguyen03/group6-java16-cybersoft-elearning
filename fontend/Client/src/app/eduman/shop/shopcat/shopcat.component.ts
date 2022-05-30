import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HomepageService } from 'src/app/share/homepage/homepage.service';

@Component({
  selector: 'app-shopcat',
  templateUrl: './shopcat.component.html',
  styleUrls: ['./shopcat.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ShopcatComponent implements OnInit {
//cart sidebar activation start
public checklist:any;
public checkedList:any;
 public masterSelected:boolean;
 private listCategories :string[] =[];
shopCatActive:boolean=false;
shopCat(){
  if(this.shopCatActive==false){
    this.shopCatActive=true;
  }
  else {
    this.shopCatActive=false;
  }
}
//cart sidebar activation end
  constructor(private _homepageService: HomepageService) {
    this.masterSelected =false;
    this.checklist = [
      {name:'Business',value:'BUSINESS',isSelected:false},
      {name:'Development',value:'DEVELOPMENT',isSelected:false},
      {name:'Design',value:'DESIGN',isSelected:false},
      {name:'Finance',value:'FINANCE',isSelected:false},
      {name:'Marketing',value:'MARKETING',isSelected:false},
      {name:'Management',value:'MANAGEMENT',isSelected:false},
      {name:'Photography',value:'PHOTOGRAPHY',isSelected:false},
      {name:'Music',value:'MUSIC',isSelected:false},
      {name:'Academic',value:'ACADEMIC',isSelected:false},
      {name:'Data Science',value:'DATA_SCIENCE',isSelected:false},
      {name:'None',value:'NONE',isSelected:false}
    ];
    this.getCheckedItemList();
  }
  ngOnInit(): void {}

  changeCategories(){
    this.getCheckedItemList();
  }
  getCheckedItemList(){
    let categories: string[] = [];
    this.checklist.map((v: any) => v.isSelected ? categories.push(v.value): '');
    this.listCategories = categories;
    this._homepageService.setCategories(this.listCategories);
  }
}
