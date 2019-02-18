export interface IAnnex {
    id?: number;
    content?: any;
}

export class Annex implements IAnnex {
    constructor(public id?: number, public content?: any) {}
}
