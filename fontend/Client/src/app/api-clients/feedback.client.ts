import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../environments/environment";
import { CommentCreate, CommentResponse, RatingCreate, RatingResponse } from "./model/feedback.model";
import { Response } from './model/common.model';
@Injectable({
    providedIn: 'root',
})
export class FeedBackClient {
    private _apiComment = `${environment.api}comments`;
    private _apiRating = `${environment.api}ratings`;
    
    constructor(protected httpClient: HttpClient) {}

    writeComment(commentCreate:CommentCreate): Observable<Response<CommentResponse>>{
        return this.httpClient.post<Response<CommentResponse>>(this._apiComment,commentCreate);
    }

    findComment(lessonId:string): Observable<Response<CommentResponse[]>>{
        return this.httpClient.get<Response<CommentResponse[]>>(this._apiComment+"/"+lessonId);

    }

    writeRating(ratingCreate:RatingCreate): Observable<Response<RatingResponse>>{
        return this.httpClient.post<Response<RatingResponse>>(this._apiRating,ratingCreate);
    }

    myRating(userId:string): Observable<Response<RatingResponse[]>>{
        return this.httpClient.get<Response<RatingResponse[]>>(this._apiRating+"/"+userId);
}
}