import { CourseDetailsReponseClientDTO } from "./course.model";

export class LessonRp {
    id?: string;
    name?: string;
    description?: string;
    content?: string;
}

export class CardLessonReponseClientDTO {
    id?: string;
    name?: string;
    description?: string;
    constructor() {
        this.id="";
        this.name="";
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

