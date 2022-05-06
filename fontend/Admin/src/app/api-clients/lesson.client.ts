import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { PageRequest, PageResponse,Response } from "./model/common.model";
import { LessonRp } from "./model/lesson.model";

@Injectable({
    providedIn: 'root',
})
export class LessonClient {
    
    private _apiEndpoint = `${environment.api}lesson`;

    constructor(protected httpClient: HttpClient) {}

    findAllCourse(pageRequet:PageRequest): Observable<Response<PageResponse<LessonRp>>>{
        return this.httpClient.post<Response<PageResponse<LessonRp>>>(this._apiEndpoint, pageRequet);
    }

    searchRequest(pageRequest:PageRequest): Observable<Response<PageResponse<LessonRp>>>{
        const options = {
            params: { ...pageRequest },
        };

        return this.httpClient.get<Response<PageResponse<LessonRp>>>(this._apiEndpoint , options);
    }
}