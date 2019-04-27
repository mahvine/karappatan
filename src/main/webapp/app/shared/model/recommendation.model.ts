import { IQuestion } from 'app/shared/model/question.model';

export interface IRecommendation {
    id?: number;
    content?: any;
    identifier?: string;
    nextRecommendationContent?: string;
    nextRecommendationId?: number;
    nextQuestions?: IQuestion[];
    moduleTitle?: string;
    moduleId?: number;
}

export class Recommendation implements IRecommendation {
    constructor(
        public id?: number,
        public content?: any,
        public identifier?: string,
        public nextRecommendationContent?: string,
        public nextRecommendationId?: number,
        public nextQuestions?: IQuestion[],
        public moduleTitle?: string,
        public moduleId?: number
    ) {}
}
