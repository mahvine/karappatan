import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICaseSummaryOffer } from 'app/shared/model/case-summary-offer.model';

type EntityResponseType = HttpResponse<ICaseSummaryOffer>;
type EntityArrayResponseType = HttpResponse<ICaseSummaryOffer[]>;

@Injectable({ providedIn: 'root' })
export class CaseSummaryOfferService {
    public resourceUrl = SERVER_API_URL + 'api/case-summary-offers';

    constructor(protected http: HttpClient) {}

    create(caseSummaryOffer: ICaseSummaryOffer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(caseSummaryOffer);
        return this.http
            .post<ICaseSummaryOffer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(caseSummaryOffer: ICaseSummaryOffer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(caseSummaryOffer);
        return this.http
            .put<ICaseSummaryOffer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICaseSummaryOffer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICaseSummaryOffer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(caseSummaryOffer: ICaseSummaryOffer): ICaseSummaryOffer {
        const copy: ICaseSummaryOffer = Object.assign({}, caseSummaryOffer, {
            dateCreated:
                caseSummaryOffer.dateCreated != null && caseSummaryOffer.dateCreated.isValid()
                    ? caseSummaryOffer.dateCreated.toJSON()
                    : null
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
            res.body.forEach((caseSummaryOffer: ICaseSummaryOffer) => {
                caseSummaryOffer.dateCreated = caseSummaryOffer.dateCreated != null ? moment(caseSummaryOffer.dateCreated) : null;
            });
        }
        return res;
    }
}
