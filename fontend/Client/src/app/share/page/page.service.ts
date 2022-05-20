import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PageService {

  getPager( currentPage: number, pageSize: number): (string | number)[] {
    let pages = [];
    let isCheck = false;
    if(currentPage != 1 && pageSize != 1) {
      pages.push('prev');
    }

    if(pageSize < 6){
      for(let i = 1; i <= pageSize; i++){
        pages.push(i);
      }
      isCheck = true;
    }

    if(!isCheck && currentPage <4){
      for(let i = 1; i <= 5; i++){
        pages.push(i);
      }
      isCheck = true;
    }

    if(!isCheck && pageSize-2 <= currentPage){
      for(let i = pageSize - 4 ; i <= pageSize; i++){
        pages.push(i);
      }
      isCheck = true;
    }

    if(!isCheck){
      for(let i = currentPage - 2 ; i <= currentPage +2; i++){
        pages.push(i);
      }
    }

    if(currentPage != pageSize) {
      pages.push('next');
    }

    return pages ;
  }
}
