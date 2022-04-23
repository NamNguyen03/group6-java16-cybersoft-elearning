
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
      pageCurrent:number;
	  itemPerPage:number;
	  fieldNameSort:String;
	  isIncrementSort:true;
	  fieldNameSearch:String;
      valueSearch:String;
      
      constructor(pageCurrent:number,
        itemPerPage:number,
        fieldNameSort:String,
        isIncrementSort:true,
        fieldNameSearch:String,
        valueSearch:String){
        this.pageCurrent = pageCurrent <=0 ? 1 : pageCurrent;
		this.itemPerPage = itemPerPage <=0 ? 10 : itemPerPage;
		this.fieldNameSort = fieldNameSort;
		this.isIncrementSort = isIncrementSort;
		this.fieldNameSearch = fieldNameSearch;
		this.valueSearch = valueSearch;
      }
}

export class SearchRequest {
    searchTerm?: string;
    sort?: string;
    pageNumber?: string;
    pageSize?: string;
    isLockout?: string;
}