
import { environment } from './../../environments/environment';
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { PageRequest, PageResponse, Response } from './model/common.model';
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

    findAllCourse(pageRequet:PageRequest): Observable<Response<PageResponse<CourseRp>>>{
        return this.httpClient.post<Response<PageResponse<CourseRp>>>(this._apiEndpoint, pageRequet);
    }

    searchRequest(pageRequest:PageRequest): Observable<Response<PageResponse<CourseRp>>>{
        const options = {
            params: { ...pageRequest },
        };

        return this.httpClient.get<Response<PageResponse<CourseRp>>>(this._apiEndpoint , options);
    }
    deleteCourse(id: string): Observable<Response<string>>{
        return this.httpClient.delete<Response<string>>(this._apiEndpoint + "/" + id);
    }

    updateCourse(id: string, course:CourseUpdateInformation): Observable<Response<CourseRp>> {
        return this.httpClient.put<Response<CourseRp>>(this._apiEndpoint+"/" + id, course);
    }

    getDetailCourse(id: string): Observable<Response<CourseRp>>{
        return this.httpClient.get<Response<CourseRp>>(this._apiEndpoint+ "/" + id);
    }

}