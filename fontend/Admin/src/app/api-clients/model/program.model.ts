
export class BaseProgram{
    name: string;
    description: string;
    type: string;
    module: string;

    constructor(name:string,description:string,type:string,module:string){
        this.name = name;
        this.description=description;
        this.type=type;
        this.module=module

    }
}
export class ProgramResponse {
    id: string;
    name: string;
    description: string;
    type: string;
    module: string;
}
export class SearchProgram{
    pageCurrent: number ;
    itemPerPage: number ;
    fieldNameSort: string;
    isIncrementSort: boolean
    fieldNameSearch: string;
    valueFieldNameSearch: string;
}




