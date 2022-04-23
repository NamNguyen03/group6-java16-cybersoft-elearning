
import { environment } from './../../environments/environment';
import { Injectable } from "@angular/core";
import { LoginRequest, UserCreate, UserRp, LoginResponse } from './model/user.model';
import { Observable } from 'rxjs';
import { PageRequestModel, PageResponseModel, Response, SearchRequest } from './model/common.model';
import { HttpClient } from '@angular/common/http';
import { CourseCreate, CourseRp } from './model/course.model';

@Injectable({
    providedIn: 'root',
})
export class CourseClient {
    private _apiEndpoint = `${environment.api}courses`

    constructor(protected httpClient: HttpClient) {}

    createCourse(course: CourseCreate): Observable<Response<CourseRp>>{

        return this.httpClient.post<Response<CourseRp>>(this._apiEndpoint, course);

    }

    findAllCourse(pageRequet:PageRequestModel): Observable<Response<PageResponseModel<CourseRp>>>{
        return this.httpClient.post<Response<PageResponseModel<CourseRp>>>(this._apiEndpoint, pageRequet);
    }

    SearchRequest(
        rq: SearchRequest = new SearchRequest()
    ): Observable<Response<PageResponseModel<CourseRp>>> {
        const options = {
            params: { ...rq },
        };

        return this.httpClient.get<Response<PageResponseModel<CourseRp>>>(this._apiEndpoint, options);
    }
}