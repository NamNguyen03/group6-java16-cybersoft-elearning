export class BaseGroup{
    name: string;
    description: string;


    constructor(name:string,description:string){
        this.name = name;
        this.description=description;
    }
}
export class GroupResponse {
    id: string;
    name: string;
    description: string;
}
export class SearchGroup{
    pageCurrent: number ;
    itemPerPage: number ;
    fieldNameSort: String;
    isIncrementSort: boolean
    fieldNameSearch: String;
    valueFieldNameSearch: String;
}
