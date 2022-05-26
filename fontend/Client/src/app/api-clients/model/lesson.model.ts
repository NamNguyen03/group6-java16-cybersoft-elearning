export class LessonRp{
    id :string;

	name:string

	content:string;

	description:string;

    starAvg:number;

	totalStar:number;

	totalRating:number;
    constructor(id:string,name:string,content:string,description:string,starAvg:number,totalStar:number,totalRating:number){
        this.id=id;
        this.name=name;
        this.content=content;
        this.description=description;
        this.starAvg=starAvg;
        this.totalStar=totalStar;
        this.totalRating=totalRating;
    }
}
