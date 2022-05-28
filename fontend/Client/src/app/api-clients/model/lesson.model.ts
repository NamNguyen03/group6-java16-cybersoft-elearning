import { CourseDetailsReponseClientDTO } from "./course.model";

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

export class CardLessonReponseClientDTO {
    id?: string;
    name?: string;
    content?: string;
    description?: string;
    constructor() {
        this.id="";
        this.name="";
        this.content="";
        this.description="";
    }
}

export class LessonDetailsResponseClientDTO {
    id?: string;
    name?: string;
    description?: string;
    content?: string;
    course?: CourseDetailsReponseClientDTO;
    constructor(
    ) {
        this.id="";
        this.name="";
        this.description="";
        this.content="";
        this.course={};
    }
}

