import { Component, OnInit } from '@angular/core';
import { HomepageService } from 'src/app/share/homepage/homepage.service';

@Component({
  selector: 'app-shop-paging',
  templateUrl: './shop-paging.component.html',
  styleUrls: ['./shop-paging.component.scss']
})
export class ShopPagingComponent implements OnInit {
  public pages: (string | number)[] = [];
  public pageCurrent: number = 1;
  constructor( private _homepageService: HomepageService) { }

  ngOnInit(): void {
    this._homepageService.$pages.subscribe(rp =>{
      this.pages = rp;
    })
  }
  clickPage(index: string | number): void {
    if(index == 'next'){
      this.pageCurrent++;
    }
    if(index == 'prev'){
      this.pageCurrent--;
    }
    if(index != 'prev' && index != 'next'){
      this.pageCurrent = Number(index);
    }
    this._homepageService.setPageCurrent(this.pageCurrent);
  }

}
