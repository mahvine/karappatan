import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICaseSummary } from 'app/shared/model/case-summary.model';

type EntityResponseType = HttpResponse<ICaseSummary>;
type EntityArrayResponseType = HttpResponse<ICaseSummary[]>;

@Injectable({ providedIn: 'root' })
export class CaseSummaryService {
    public resourceUrl = SERVER_API_URL + 'api/case-summaries';

    constructor(protected http: HttpClient) {}

    create(caseSummary: ICaseSummary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(caseSummary);
        return this.http
            .post<ICaseSummary>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(caseSummary: ICaseSummary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(caseSummary);
        return this.http
            .put<ICaseSummary>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICaseSummary>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICaseSummary[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
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
}
