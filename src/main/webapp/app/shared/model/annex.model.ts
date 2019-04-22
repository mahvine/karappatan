import { IQuestion } from 'app/shared/model/question.model';

export interface IAnnex {
    id?: number;
    content?: any;
    identifier?: string;
    nextQuestions?: IQuestion[];
}

export class Annex implements IAnnex {
    constructor(public id?: number, public content?: any, public identifier?: string, public nextQuestions?: IQuestion[]) {}
}
