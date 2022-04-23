
import { environment } from './../../environments/environment';
import { Injectable } from "@angular/core";
import { LoginRequest, UserCreate, UserRp, LoginResponse } from './model/user.model';
import { Observable } from 'rxjs';
import { Response } from './model/common.model';
import { HttpClient } from '@angular/common/http';
import { CourseCreate, CourseRp } from './model/course.model';

@Injectable({
    providedIn: 'root',
})
export class CourseClient {
    private _apiEndpoint = `${environment.api}courses`
    private _apiLogin = `${environment.api}auth/login`;

    constructor(protected httpClient: HttpClient) {}

    createCourse(course: CourseCreate): Observable<Response<CourseRp>>{

        return this.httpClient.post<Response<CourseRp>>(this._apiEndpoint, course);

    }

}