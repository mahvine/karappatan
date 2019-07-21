import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICaseSummary } from 'app/shared/model/case-summary.model';
import { ICaseSummaryOffer } from 'app/shared/model/case-summary-offer.model';

type EntityResponseType = HttpResponse<ICaseSummary>;
type EntityArrayResponseType = HttpResponse<ICaseSummary[]>;

@Injectable({ providedIn: 'root' })
export class KarappatanService {
    public resourceUrl = SERVER_API_URL + 'api/caseSummaries';

    constructor(protected http: HttpClient) {}

    accept(caseSummaryId: number): Observable<EntityResponseType> {
        const url = this.resourceUrl + '/' + caseSummaryId + '/accept';
        return this.http
            .post<ICaseSummary>(url, {}, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    protected convertDateFromClient(caseSummary: ICaseSummary): ICaseSummary {
        const copy: ICaseSummary = Object.assign({}, caseSummary, {
            dateCreated: caseSummary.dateCreated != null && caseSummary.dateCreated.isValid() ? caseSummary.dateCreated.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateCreated = res.body.dateCreated != null ? moment(res.body.dateCreated) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((caseSummary: ICaseSummary) => {
                caseSummary.dateCreated = caseSummary.dateCreated != null ? moment(caseSummary.dateCreated) : null;
            });
        }
        return res;
    }

    offer(caseSummaryId: number): Observable<EntityResponseType> {
        const url = this.resourceUrl + '/offers';
        return this.http
            .post<ICaseSummary>(url, { caseSummaryId: caseSummaryId }, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    listOffers(caseSummaryId: number): Observable<EntityArrayResponseType> {
        return this.http
            .get<ICaseSummaryOffer[]>(this.resourceUrl + '/' + caseSummaryId + '/offers', { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }
}
