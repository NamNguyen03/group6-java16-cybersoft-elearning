import { environment } from './../../environments/environment';
import { Injectable } from "@angular/core";
import { LoginRequest, UserCreate, UserRp, LoginResponse } from './model/user.model';
import { Observable } from 'rxjs';
import { PageRequest, PageResponse, Response } from './model/common.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class UserClient {
    private _apiEndpoint = `${environment.api}users`
    private _apiLogin = `${environment.api}auth/login`;

    constructor(protected httpClient: HttpClient) {}

    createUser(user: UserCreate): Observable<Response<UserRp>>{

        return this.httpClient.post<Response<UserRp>>(this._apiEndpoint, user);

    }

    login(rq: LoginRequest): Observable<Response<LoginResponse>>{
        return this.httpClient.post<Response<LoginResponse>>(this._apiLogin, rq);

    }   

    searchUsers(
        rq: any
    ): Observable<Response<UserRp>> {
        const options = {
            params: { ...rq },
        };

        return this.httpClient.get<Response<UserRp>>(this._apiEndpoint, options);
    }

    searchAll(pageRequest:PageRequest): Observable<Response<PageResponse<UserRp>>>{
        const options = {
            params: { ...pageRequest },
        };

        return this.httpClient.get<Response<PageResponse<UserRp>>>(this._apiEndpoint , options);

    
    }
}