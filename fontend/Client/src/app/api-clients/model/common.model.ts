
export class Response<T>{
    content!: T;
    hasErrors!: boolean;
    errors!: string;
    timeStamp!: Date;
    status!: number;
}
export class PageResponse<T>{
    pageCurrent?: number;
    totalPage?: number;
    items?: T[] ;
}
export class PageRequest{
    pageCurrent: number;
    itemPerPage: number;
    name: string ;
    categories: string[] ;
    rating: number ;
    fromTime: number ;
    toTime: number ;
    level: string[] ;

    constructor(
        pageCurrent: number,
        itemPerPage: number,
        name:string,
        rating: number,
        fromTime:number,
        toTime: number,
        level: string[],
        categories:string[],
        ){
        
        this.pageCurrent = pageCurrent <=0 ? 1 : pageCurrent;
        this.itemPerPage = itemPerPage <=0 ? 10 : itemPerPage;
        this.name =name;
		this.rating = rating;
        this.fromTime=fromTime;
		this.toTime = toTime;
		this.level = level;
		this.categories = categories;

    }
    

}
    

