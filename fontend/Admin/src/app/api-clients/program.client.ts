import { environment } from './../../environments/environment';
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { Response,PageRequest, PageResponse } from './model/common.model';
import { HttpClient } from '@angular/common/http';
import { BaseProgram, ProgramResponse } from './model/program.model';
@Injectable({
    providedIn: 'root',
})
export class ProgramClient {
    private apiEndpoint  = `${environment.api}programs`

    constructor(protected httpClient: HttpClient) {}

    createProgram(role: BaseProgram): Observable<Response<ProgramResponse>>{

        return this.httpClient.post<Response<ProgramResponse>>(this.apiEndpoint , role);

    }
    search(pageRequest:PageRequest): Observable<Response<PageResponse<ProgramResponse>>>{
        const options = {
            params: { ...pageRequest },
        };

        return this.httpClient.get<Response<PageResponse<ProgramResponse>>>(this.apiEndpoint , options);
    }

    deleteById(id:string): Observable<Response<string>>{
        
        
        return this.httpClient.delete<Response<string>>(this.apiEndpoint+"/"+id);

    }

    updateById(id:string,rq:BaseProgram ): Observable<Response<ProgramResponse>>{
        
        return this.httpClient.put<Response<ProgramResponse>>(this.apiEndpoint+"/"+id,rq);

    }
}