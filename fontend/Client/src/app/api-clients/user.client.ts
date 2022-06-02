import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { InstructorCourseClientDTO, LoginRequest, RegisterRequest, UserRp } from "./model/user.model";
import { PageRequest, PageResponse, Response } from './model/common.model';
import { environment } from "../environments/environment";
@Injectable({
    providedIn: 'root',
})
export class UserClient {
    private _apiEndpoint = `${environment.api}users`;
    private _apiLogin = `${environment.api}auth/login`;
    private _apiRegister = `${environment.api}auth/register`;
    private _apiMyProfile = `${this._apiEndpoint}/me`;
    private _apiMyProfileFindUserName =  `${environment.api}users/client`;
    
    constructor(protected httpClient: HttpClient) {}

    login(rq: LoginRequest): Observable<Response<string>>{
        return this.httpClient.post<Response<string>>(this._apiLogin, rq);

    }   

    getMyProfile(): Observable<Response<UserRp>>{
        return this.httpClient.get<Response<UserRp>>(this._apiMyProfile);
    }

    getMyProfileFindUserName(username: string): Observable<Response<InstructorCourseClientDTO>>{
        return this.httpClient.get<Response<InstructorCourseClientDTO>>(this._apiMyProfileFindUserName + "/" + username);
    }

    register(rq: RegisterRequest): Observable<Response<UserRp>>{
        return this.httpClient.post<Response<UserRp>>(this._apiRegister, rq)
    }

    searchRequest(pageRequest: PageRequest): Observable<Response<PageResponse<UserRp>>> {

        let params = new HttpParams();
        params = params.append('pageCurrent', pageRequest.pageCurrent);
        params = params.append('itemPerPage', pageRequest.itemPerPage);
        params = params.append('name', pageRequest.name == undefined ? "" : pageRequest.name);
        params = params.append('rating', pageRequest.rating == undefined ? "" : pageRequest.rating);
        return this.httpClient.get<Response<PageResponse<UserRp>>>(this._apiEndpoint, { params: params });
    }
    
}