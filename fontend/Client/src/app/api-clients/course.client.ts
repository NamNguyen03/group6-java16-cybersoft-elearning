import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PageRequest, PageResponse, Response } from './model/common.model';
import { environment } from "../environments/environment";
import { CourseRp } from "./model/course.model";
@Injectable({
    providedIn: 'root',
})
export class CourseClient {
    private _apiEndpoint = `${environment.api}courses`;
  
    constructor(protected httpClient: HttpClient) {}

    searchRequest(pageRequest:PageRequest): Observable<Response<PageResponse<CourseRp>>>{
        const options = {
            params: { ...pageRequest },
        };
        return this.httpClient.get<Response<PageResponse<CourseRp>>>(this._apiEndpoint , options);
    }

}