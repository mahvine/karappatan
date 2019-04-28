import { Moment } from 'moment';
import { IAnswer } from 'app/shared/model/answer.model';

export interface ICaseSummary {
    id?: number;
    dateCreated?: Moment;
    userLogin?: string;
    userId?: number;
    answers?: IAnswer[];
    moduleTitle?: string;
    moduleId?: number;
    answerIds?: number[];
    acceptedByLogin?: string;
    user?: any;
}

export class CaseSummary implements ICaseSummary {
    constructor(
        public id?: number,
        public dateCreated?: Moment,
        public userLogin?: string,
        public userId?: number,
        public answers?: IAnswer[],
        public moduleTitle?: string,
        public moduleId?: number,
        public answerIds?: number[],
        public acceptedByLogin?: string,
        public user?: any
    ) {}
}
