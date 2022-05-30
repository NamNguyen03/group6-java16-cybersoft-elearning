import { CourseDetailsReponseClientDTO } from "./course.model";

export class LessonRp{
    id :string;

	name:string

	content:string;

	description:string;

    starAvg:number;

	totalStar:number;

	totalRating:number;

    totalOneStar:number;

    totalTwoStar:number;

    totalThreeStar:number;

    totalFourStar:number;

    totalFiveStar:number;
    constructor(
        id:string,
        name:string,
        content:string,
        description:string,
        starAvg:number,
        totalStar:number,
        totalRating:number,
        totalOneStar:number,
        totalTwoStar:number,
        totalThreeStar:number,
        totalFourStar:number,
        totalFiveStar:number)
        {
        this.id=id;
        this.name=name;
        this.content=content;
        this.description=description;
        this.starAvg=starAvg;
        this.totalStar=totalStar;
        this.totalRating=totalRating;
        this.totalOneStar=totalOneStar;
        this.totalTwoStar=totalTwoStar;
        this.totalThreeStar=totalThreeStar;
        this.totalFourStar=totalFourStar;
        this.totalFiveStar=totalFiveStar;
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
    starAvg:number;

	totalStar:number;

	totalRating:number;

    totalOneStar:number;

    totalTwoStar:number;

    totalThreeStar:number;

    totalFourStar:number;

    totalFiveStar:number;
    constructor(
    ) {
        this.id="";
        this.name="";
        this.description="";
        this.content="";
        this.course={};
        this.starAvg=0;
        this.totalStar=0;
        this.totalRating=0;
        this.totalOneStar=0;
        this.totalTwoStar=0;
        this.totalThreeStar=0;
        this.totalFourStar=0;
        this.totalFiveStar=0;
    }
}

