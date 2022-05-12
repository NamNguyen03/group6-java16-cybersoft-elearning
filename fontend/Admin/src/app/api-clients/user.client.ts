import { environment } from './../../environments/environment';
import { Injectable } from "@angular/core";
import { LoginRequest, UserCreate, UserRp, UpdateMyProfileRq, UpdateUserRq } from './model/user.model';
import { Observable } from 'rxjs';
import { PageRequest, PageResponse, Response } from './model/common.model';
import { HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class UserClient {
    private _apiEndpoint = `${environment.api}users`;
    private _apiLogin = `${environment.api}auth/login`;
    private _apiMyProfile = `${this._apiEndpoint}/me`;

    constructor(protected httpClient: HttpClient) {}

    createUser(user: UserCreate): Observable<Response<UserRp>>{

        return this.httpClient.post<Response<UserRp>>(this._apiEndpoint, user);

    }

    login(rq: LoginRequest): Observable<Response<string>>{
        return this.httpClient.post<Response<string>>(this._apiLogin, rq);

    }   

    search(pageRequest:PageRequest): Observable<Response<PageResponse<UserRp>>>{
        const options = {
            params: { ...pageRequest },
        };
        return this.httpClient.get<Response<PageResponse<UserRp>>>(this._apiEndpoint , options);
    }

    delete(id: string): Observable<Response<string>>{
        return this.httpClient.delete<Response<string>>(this._apiEndpoint + "/" + id);
    }

    getMyProfile(): Observable<Response<UserRp>>{
        return this.httpClient.get<Response<UserRp>>(this._apiMyProfile);
    }

    updateMyProfile(rq: UpdateMyProfileRq): Observable<Response<UserRp>>{
        return this.httpClient.put<Response<UserRp>>(this._apiMyProfile, rq)
    }

    updateUser(id, rq: UpdateUserRq): Observable<Response<UserRp>>{
        return this.httpClient.put<Response<UserRp>>(this._apiEndpoint + "/" + id, rq)
    }
}