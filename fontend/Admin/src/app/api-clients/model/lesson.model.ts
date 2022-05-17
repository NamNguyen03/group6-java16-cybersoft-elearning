export class LessonRp {
    id!: string;
    name!: string;
    description!: string;
    content!: string;
}

export class LessonCreate {

    name: string;
    content: string;
    description: string;
    course_id: string;

    constructor(name: string,content: string,description: string,course_id: string) {
        this.name = name;
        this.content = content;
        this.description = description;
        this.course_id = course_id;
    }
}

export class UpdateLesson{

    name: string;
    content: string;
    description: string;

    constructor(name: string,content: string,description: string) {
        this.name = name;
        this.content = content;
        this.description = description;
    }
}