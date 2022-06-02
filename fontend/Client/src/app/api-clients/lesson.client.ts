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
    private _apiEndpoint = `${environment.api}lessons/client`;
  
    constructor(protected httpClient: HttpClient) {}

    getLessonDetails(id: string): Observable<Response<LessonDetailsResponseClientDTO>>{
        return this.httpClient.get<Response<LessonDetailsResponseClientDTO>>(this._apiEndpoint+ "/" + id);
    }
}