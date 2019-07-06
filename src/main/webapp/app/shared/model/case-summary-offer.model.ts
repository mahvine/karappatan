import { Moment } from 'moment';
import { ICaseSummary } from 'app/shared/model/case-summary.model';
import { IUser } from 'app/core/user/user.model';

export const enum OfferStatus {
    OPEN = 'OPEN',
    DENIED = 'DENIED',
    ACCEPTED = 'ACCEPTED'
}

export interface ICaseSummaryOffer {
    id?: number;
    dateCreated?: Moment;
    status?: OfferStatus;
    caseSummary?: ICaseSummary;
    lawyer?: IUser;
}

export class CaseSummaryOffer implements ICaseSummaryOffer {
    constructor(
        public id?: number,
        public dateCreated?: Moment,
        public status?: OfferStatus,
        public caseSummary?: ICaseSummary,
        public lawyer?: IUser
    ) {}
}
