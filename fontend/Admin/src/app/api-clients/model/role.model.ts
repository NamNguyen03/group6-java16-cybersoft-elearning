import { ProgramResponse } from "./program.model";

export class BaseRole{
    name: string;
    description: string;


    constructor(name:string,description:string){
        this.name = name;
        this.description=description;
    }
}
export class RoleResponse {
    id: string;
    name: string;
    description: string;
    programs: ProgramResponse[]; 
}
export class SearchRole{
    pageCurrent: number ;
    itemPerPage: number ;
    fieldNameSort: String;
    isIncrementSort: boolean
    fieldNameSearch: String;
    valueFieldNameSearch: String;
}




