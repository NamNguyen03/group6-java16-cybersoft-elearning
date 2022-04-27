import { environment } from './../../environments/environment';
import { Injectable } from "@angular/core";
import { BaseRole, RoleResponse,SearchRole } from './model/role.model';
import { Observable } from 'rxjs';
import { Response,PageRequest, PageResponse } from './model/common.model';
import { HttpClient } from '@angular/common/http';
@Injectable({
    providedIn: 'root',
})
export class RoleClient {
    private apiEndpoint  = `${environment.api}roles`

    constructor(protected httpClient: HttpClient) {}

    createRole(role: BaseRole): Observable<Response<RoleResponse>>{

        return this.httpClient.post<Response<RoleResponse>>(this.apiEndpoint , role);

    }
    
    searchAll(pageRequest:PageRequest): Observable<Response<PageResponse<RoleResponse>>>{
        const options = {
            params: { ...pageRequest },
        };

        return this.httpClient.get<Response<PageResponse<RoleResponse>>>(this.apiEndpoint , options);
    }

    deleteById(id:string): Observable<Response<string>>{
        
        
        return this.httpClient.delete<Response<string>>(this.apiEndpoint+"/"+id);

    }

    updateById(id:string,rq:BaseRole): Observable<Response<RoleResponse>>{
        
        return this.httpClient.put<Response<RoleResponse>>(this.apiEndpoint+"/"+id,rq);

    }
}