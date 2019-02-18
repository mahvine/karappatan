import { IQuestion } from 'app/shared/model/question.model';
import { IAnnex } from 'app/shared/model/annex.model';
import { IRecommendation } from 'app/shared/model/recommendation.model';

export interface IModule {
    id?: number;
    title?: string;
    details?: string;
    questions?: IQuestion[];
    annexes?: IAnnex[];
    recommendations?: IRecommendation[];
}

export class Module implements IModule {
    constructor(
        public id?: number,
        public title?: string,
        public details?: string,
        public questions?: IQuestion[],
        public annexes?: IAnnex[],
        public recommendations?: IRecommendation[]
    ) {}
}
