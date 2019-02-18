import { IAnswer } from 'app/shared/model/answer.model';

export interface IQuestion {
    id?: number;
    question?: string;
    answers?: IAnswer[];
}

export class Question implements IQuestion {
    constructor(public id?: number, public question?: string, public answers?: IAnswer[]) {}
}
