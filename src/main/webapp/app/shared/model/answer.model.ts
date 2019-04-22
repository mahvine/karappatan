import { IAnnex } from 'app/shared/model/annex.model';
import { IRecommendation } from 'app/shared/model/recommendation.model';
import { IQuestion } from 'app/shared/model/question.model';

export interface IAnswer {
    id?: number;
    answer?: any;
    instructions?: any;
    annex?: IAnnex;
    recommendation?: IRecommendation;
    nextQuestion?: IQuestion;
    question?: IQuestion;
}

export class Answer implements IAnswer {
    constructor(
        public id?: number,
        public answer?: any,
        public instructions?: any,
        public annex?: IAnnex,
        public recommendation?: IRecommendation,
        public nextQuestion?: IQuestion,
        public question?: IQuestion
    ) {}
}
