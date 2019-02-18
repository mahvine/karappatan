export interface IRecommendation {
    id?: number;
    content?: any;
}

export class Recommendation implements IRecommendation {
    constructor(public id?: number, public content?: any) {}
}
