import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { PageRequest, PageResponse, Response } from "./model/common.model";
import { StatusCommentRp } from "./model/status-comment.model";


@Injectable({
    providedIn: 'root',
})
export class StatusCommentClient {

    private _apiEndpoint  = `${environment.api}status-comments`
    
    constructor(protected httpClient: HttpClient) {}

    search(pageRequest:PageRequest): Observable<Response<PageResponse<StatusCommentRp>>>{
        const options = {
            params: { ...pageRequest },
        };
        return this.httpClient.get<Response<PageResponse<StatusCommentRp>>>(this._apiEndpoint , options);
    }

    update(id: string , status: string): Observable<Response<StatusCommentRp>>{
        return this.httpClient.put<Response<StatusCommentRp>>(this._apiEndpoint + '/' + id, {status});
    }
}