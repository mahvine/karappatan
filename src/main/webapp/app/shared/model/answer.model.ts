import { IAnnex } from 'app/shared/model/annex.model';
import { IRecommendation } from 'app/shared/model/recommendation.model';
import { IQuestion } from 'app/shared/model/question.model';

export interface IAnswer {
    id?: number;
    answer?: string;
    instructions?: string;
    annex?: IAnnex;
    recommendation?: IRecommendation;
    nextQuestion?: IQuestion;
}

export class Answer implements IAnswer {
    constructor(
        public id?: number,
        public answer?: string,
        public instructions?: string,
        public annex?: IAnnex,
        public recommendation?: IRecommendation,
        public nextQuestion?: IQuestion
    ) {}
}
