
export class Response<T>{
    content!: T;
    hasErrors!: boolean;
    errors!: string;
    timeStamp!: Date;
    status!: number;
}

export class PageResponseModel<T>{
    totalPage: number;
    pageCurrent: number;
    items: T[];
}

export class PageRequestModel{
    pageCurrent: number;
    itemPerPage: number;
    fieldNameSort: string;
    isIncrementSort: boolean;
    fieldNameSearch: string;
    valueFieldNameSearch: string;

    constructor(
        pageCurrent: number,
        itemPerPage: number,
        fieldNameSort: string,
        isIncrementSort: boolean,
        fieldNameSearch: string,
        valueFieldNameSearch: string){
        
        this.pageCurrent = pageCurrent <=0 ? 1 : pageCurrent;
        this.itemPerPage = itemPerPage <=0 ? 10 : itemPerPage;
		this.fieldNameSort = fieldNameSort;
		this.isIncrementSort = isIncrementSort;
		this.fieldNameSearch = fieldNameSearch;
		this.valueFieldNameSearch = valueFieldNameSearch;

      }
}