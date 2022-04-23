
export class Response<T>{
    content!: T;
    hasErrors!: boolean;
    errors!: string;
    timeStamp!: Date;
    status!: number;
}

export class PageResponse<T>{
    pageCurrent: number;
    totalPage: number;
    items : T[] ;
}
export class PageRequest{
    pageCurrent: number;
    itemPerPage: number;
    fieldNameSort: string;
    isIncrementSort: boolean;
    fieldNameSearch: string;
    valueSearch: string;

    constructor( pageCurrent: number,
        itemPerPage: number,
        fieldNameSort: string,
        isIncrementSort: boolean,
        fieldNameSearch: string,
        valueSearch: string){
        
            this.pageCurrent = pageCurrent <=0 ? 1 : pageCurrent;
		this.itemPerPage = itemPerPage <=0 ? 10 : itemPerPage;
		this.fieldNameSort = fieldNameSort;
		this.isIncrementSort = isIncrementSort;
		this.fieldNameSearch = fieldNameSearch;
		this.valueSearch = valueSearch;


    }
}