export interface IAnswer {
    id?: number;
    answer?: any;
    instructions?: any;
    annexContent?: string;
    annexId?: number;
    recommendationContent?: string;
    recommendationId?: number;
    nextQuestionQuestion?: string;
    nextQuestionId?: number;
    questionQuestion?: string;
    questionId?: number;
}

export class Answer implements IAnswer {
    constructor(
        public id?: number,
        public answer?: any,
        public instructions?: any,
        public annexContent?: string,
        public annexId?: number,
        public recommendationContent?: string,
        public recommendationId?: number,
        public nextQuestionQuestion?: string,
        public nextQuestionId?: number,
        public questionQuestion?: string,
        public questionId?: number
    ) {}
}
