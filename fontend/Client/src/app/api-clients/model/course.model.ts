export class CourseCreate{

    name: string;
    description: string;
    // img : string;
    // starAVG: number;
     totalTime : number
    // level : string;
    // skills: string []=[];
    // category: string;

    constructor(coursename: string, coursetime: number, description: string){
        this.name = coursename;
        this.totalTime = coursetime;
        this.description = description;
    }
}
export class CourseRp{
    id!: string;
    name!: string;
    totalTime!: number;
    description!: string;
}

export class CourseUpdateInformation {
    name!: string;
    totalTime!: number;
    description!: string;

    constructor(coursename: string, coursetime: number, description: string){
        this.name = coursename;
        this.totalTime = coursetime;
        this.description = description;
}
}


