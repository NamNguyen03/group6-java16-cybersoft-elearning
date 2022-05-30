import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CourseClient } from 'src/app/api-clients/course.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseRp } from 'src/app/api-clients/model/course.model';
import { PageService } from '../page/page.service';

@Injectable({
  providedIn: 'root'
})
export class HomepageService {
  private _$data: BehaviorSubject<CourseRp[]> = new BehaviorSubject([new CourseRp()]);
  public readonly $data: Observable<CourseRp[]> = this._$data.asObservable();

  private _$pages: BehaviorSubject< (string | number)[] > = new BehaviorSubject([1,'']);
  public readonly $pages: Observable< (string | number)[] > = this._$pages.asObservable();

  private _pageRequest: PageRequest = new PageRequest(1, 12, '', 0, 0, 100, [],  []);
  constructor(  private courseClient: CourseClient,
    private _pageService: PageService) { 
      this.loadData();
    }

  loadData() {
    this.courseClient.searchRequest(this._pageRequest).subscribe(
      response =>{
        this._$data.next(response.content.items||[]);
        this._$pages.next(this._pageService.getPager(response.content.pageCurrent ? response.content.pageCurrent : 1 , response.content.totalPage ? response.content.totalPage : 1 ));
    });  
  }

  setValueSearch(value: string){
    this._pageRequest.name = value;
    this.loadData();
  }

  setPageCurrent(pageCurrent: number){
    this._pageRequest.pageCurrent = pageCurrent;
    this.loadData();
  }

  setTime(fromLesson: number,toLesson: number){
    this._pageRequest.fromTime = fromLesson;
    this._pageRequest.toTime = toLesson;
    this.loadData();
  }
  setRating(rating: number){
    this._pageRequest.rating = rating;
    this.loadData();
  }
  setCategories(categories:string[]){
    this._pageRequest.categories=categories;
    this.loadData();
  }
  setLevels(levels:string[]){
    this._pageRequest.level=levels;
    this.loadData();
  }
}
