import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginRequest, RegisterRequest, UserRp } from "./model/user.model";
import { Response } from './model/common.model';
import { environment } from "../environments/environment";
@Injectable({
    providedIn: 'root',
})
export class UserClient {
    private _apiEndpoint = `${environment.api}users`;
    private _apiLogin = `${environment.api}auth/login`;
    private _apiRegister = `${environment.api}auth/register`;
    private _apiMyProfile = `${this._apiEndpoint}/me`;
    
    constructor(protected httpClient: HttpClient) {}

    login(rq: LoginRequest): Observable<Response<string>>{
        return this.httpClient.post<Response<string>>(this._apiLogin, rq);

    }   

    getMyProfile(): Observable<Response<UserRp>>{
        return this.httpClient.get<Response<UserRp>>(this._apiMyProfile);
    }

    register(rq: RegisterRequest): Observable<Response<UserRp>>{
        return this.httpClient.post<Response<UserRp>>(this._apiRegister, rq)
    }
}