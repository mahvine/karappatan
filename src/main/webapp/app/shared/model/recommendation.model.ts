import { IRecommendation } from 'app/shared/model/recommendation.model';
import { IQuestion } from 'app/shared/model/question.model';

export interface IRecommendation {
    id?: number;
    content?: any;
    identifier?: string;
    nextRecommendation?: IRecommendation;
    nextQuestions?: IQuestion[];
}

export class Recommendation implements IRecommendation {
    constructor(
        public id?: number,
        public content?: any,
        public identifier?: string,
        public nextRecommendation?: IRecommendation,
        public nextQuestions?: IQuestion[]
    ) {}
}
