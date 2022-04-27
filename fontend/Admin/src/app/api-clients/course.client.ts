
import { environment } from './../../environments/environment';
import { Injectable } from "@angular/core";
import { LoginRequest, UserCreate, UserRp, LoginResponse } from './model/user.model';
import { Observable } from 'rxjs';
import { PageRequestModel, PageResponseModel, Response } from './model/common.model';
import { HttpClient } from '@angular/common/http';
import { CourseCreate, CourseRp, CourseUpdateInformation } from './model/course.model';

@Injectable({
    providedIn: 'root',
})
export class CourseClient {
    
    private _apiEndpoint = `${environment.api}courses`;

    constructor(protected httpClient: HttpClient) {}

    createCourse(course: CourseCreate): Observable<Response<CourseRp>>{

        return this.httpClient.post<Response<CourseRp>>(this._apiEndpoint, course);

    }

    findAllCourse(pageRequet:PageRequestModel): Observable<Response<PageResponseModel<CourseRp>>>{
        return this.httpClient.post<Response<PageResponseModel<CourseRp>>>(this._apiEndpoint, pageRequet);
    }

    searchRequest(pageRequest:PageRequestModel): Observable<Response<PageResponseModel<CourseRp>>>{
        const options = {
            params: { ...pageRequest },
        };

        return this.httpClient.get<Response<PageResponseModel<CourseRp>>>(this._apiEndpoint , options);
    }
    deleteCourse(id: string): Observable<Response<string>>{
        return this.httpClient.delete<Response<string>>(this._apiEndpoint + "/" + id);
    }

    updateCourse(id: string, course:CourseUpdateInformation): Observable<Response<CourseRp>> {
        return this.httpClient.put<Response<CourseRp>>(this._apiEndpoint+"/" + id, course);
    }
}