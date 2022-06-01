
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
    name: string | undefined;
    categories: string[] | undefined;
    rating: number | undefined;
    time: number | undefined;
    level: string[] | undefined;;

    constructor(
        pageCurrent: number,
        itemPerPage: number,
        rating: number,
        time: number,
        level: string[],
        categories:string[],

        ){
        
        this.pageCurrent = pageCurrent <=0 ? 1 : pageCurrent;
        this.itemPerPage = itemPerPage <=0 ? 10 : itemPerPage;
		this.rating = rating;
		this.time = time;
		this.level = level;
		this.categories = categories;

    }
    

}
    

