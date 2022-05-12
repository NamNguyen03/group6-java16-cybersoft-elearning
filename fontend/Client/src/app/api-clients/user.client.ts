import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginRequest } from "./model/user.model";
import { Response } from './model/common.model';
import { environment } from "../environments/environment";
@Injectable({
    providedIn: 'root',
})
export class UserClient {
    private _apiEndpoint = `${environment.api}users`;
    private _apiLogin = `${environment.api}auth/login`;

    constructor(protected httpClient: HttpClient) {}

    login(rq: LoginRequest): Observable<Response<string>>{
        return this.httpClient.post<Response<string>>(this._apiLogin, rq);

    }   
}