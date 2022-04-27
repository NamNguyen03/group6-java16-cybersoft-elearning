import { environment } from './../../environments/environment';
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { BaseGroup, GroupResponse } from './model/group.model';
import { PageRequest, PageResponse,Response } from './model/common.model';
@Injectable({
    providedIn: 'root',
})
export class GroupClient {
    private apiEndpoint  = `${environment.api}groups`

    constructor(protected httpClient: HttpClient) {}

    createGroup(group: BaseGroup): Observable<Response<GroupResponse>>{

        return this.httpClient.post<Response<GroupResponse>>(this.apiEndpoint , group);

    }

    search(pageRequest:PageRequest): Observable<Response<PageResponse<GroupResponse>>>{
        const options = {
            params: { ...pageRequest },
        };

        return this.httpClient.get<Response<PageResponse<GroupResponse>>>(this.apiEndpoint , options);
    }

    deleteById(id:string): Observable<Response<string>>{
        
        
        return this.httpClient.delete<Response<string>>(this.apiEndpoint+"/"+id);

    }
    
    updateById(id:string,rq:BaseGroup): Observable<Response<GroupResponse>>{
        
        
        return this.httpClient.put<Response<GroupResponse>>(this.apiEndpoint+"/"+id,rq);

    }
}