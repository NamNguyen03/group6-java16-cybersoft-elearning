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
    courseId: string;

    constructor(name: string,content: string,description: string,courseId: string) {
        this.name = name;
        this.content = content;
        this.description = description;
        this.courseId = courseId;
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