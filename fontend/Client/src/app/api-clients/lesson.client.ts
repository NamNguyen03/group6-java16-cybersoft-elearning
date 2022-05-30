import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PageRequest, PageResponse, Response } from './model/common.model';
import { environment } from "../environments/environment";
import { LessonDetailsResponseClientDTO, LessonRp } from "./model/lesson.model";
@Injectable({
    providedIn: 'root',
})
export class LessonClient {
    getInfoLesson(id: any) {
      throw new Error('Method not implemented.');
    }
    private _apiEndpoint = `${environment.api}lessons/client`;

    searchRequest(pageRequest: PageRequest): Observable<Response<PageResponse<LessonDetailsResponseClientDTO>>> {


        const categories = pageRequest.categories == undefined ? "" : pageRequest.categories;
        const levels =pageRequest.level == undefined ? "" : pageRequest.level;

        let params = new HttpParams();
        params = params.append('pageCurrent', pageRequest.pageCurrent);
        params = params.append('itemPerPage', pageRequest.itemPerPage);
        params = params.append('name', pageRequest.name == undefined ? "" : pageRequest.name);
        params = params.append('rating', pageRequest.rating == undefined ? "" : pageRequest.rating);
        params = params.append('time', pageRequest.time == undefined ? "" : pageRequest.time);
        for (const categorie of categories) {
            params = params.append('categories', categorie);
        }

        for (const level of levels) {
            params = params.append('level', level);
        }
        return this.httpClient.get<Response<PageResponse<LessonDetailsResponseClientDTO>>>(this._apiEndpoint, { params: params });
    }
  
    constructor(protected httpClient: HttpClient) {}

    getLessonDetails(id: string): Observable<Response<LessonDetailsResponseClientDTO>>{
        return this.httpClient.get<Response<LessonDetailsResponseClientDTO>>(this._apiEndpoint+ "/" + id);
    }
}