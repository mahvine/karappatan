import { IAnswer } from 'app/shared/model/answer.model';

export interface IQuestion {
    id?: number;
    question?: any;
    identifier?: string;
    info?: any;
    answers?: IAnswer[];
}

export class Question implements IQuestion {
    constructor(public id?: number, public question?: any, public identifier?: string, public info?: any, public answers?: IAnswer[]) {}
}
