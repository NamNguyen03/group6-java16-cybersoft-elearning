import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { PageRequest, PageResponse,Response } from "./model/common.model";
import { LessonCreate, LessonRp, UpdateLesson } from "./model/lesson.model";

@Injectable({
    providedIn: 'root',
})
export class LessonClient {
    
    private _apiEndpoint = `${environment.api}lessons`;

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

    deleteLesson(id: string): Observable<Response<string>>{
        return this.httpClient.delete<Response<string>>(this._apiEndpoint + "/" + id);
    }

    createLesson(lesson: LessonCreate): Observable<Response<LessonRp>>{

        return this.httpClient.post<Response<LessonRp>>(this._apiEndpoint, lesson);

    }

    getInfoLesson(id: string): Observable<Response<LessonRp>>{
        return this.httpClient.get<Response<LessonRp>>(this._apiEndpoint+ "/" + id);
    }

    updateLesson(id: string, lesson:UpdateLesson): Observable<Response<LessonRp>> {
        return this.httpClient.put<Response<LessonRp>>(this._apiEndpoint+"/" + id, lesson);
    }

    
}