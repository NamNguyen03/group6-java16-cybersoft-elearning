import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HomepageService } from 'src/app/share/homepage/homepage.service';

@Component({
  selector: 'app-shoplevel',
  templateUrl: './shoplevel.component.html',
  styleUrls: ['./shoplevel.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ShoplevelComponent implements OnInit {
  public checklist:any;
  public masterSelected:boolean;
  public levels :string[]=[];
  shopLevelActive:boolean=false;
  shopLevel(){
    if(this.shopLevelActive==false){
      this.shopLevelActive=true;
    }
    else {
      this.shopLevelActive=false;
    }
  }
  constructor(private _homepageService: HomepageService) {
    this.masterSelected =false;
    this.checklist = [
      {name:'Beginner',value:'BEGINNER',isSelected:false},
      {name:'Middle',value:'MIDDLE',isSelected:false},
      {name:'Master',value:'MASTER',isSelected:false},
      {name:'All',value:'ALL',isSelected:false}
    ];
    this.getCheckedItemList();
  }
  ngOnInit(): void {}

  changeLevel(){
    this.getCheckedItemList();
  }
  getCheckedItemList(){
    let levels: string[] = [];
    this.checklist.map((v: any) => v.isSelected ? levels.push(v.value): '');
    this.levels = levels;
    this._homepageService.setLevels(this.levels);
  }
}