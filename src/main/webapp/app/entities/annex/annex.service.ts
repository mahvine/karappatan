import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnnex } from 'app/shared/model/annex.model';

type EntityResponseType = HttpResponse<IAnnex>;
type EntityArrayResponseType = HttpResponse<IAnnex[]>;

@Injectable({ providedIn: 'root' })
export class AnnexService {
    public resourceUrl = SERVER_API_URL + 'api/annexes';

    constructor(protected http: HttpClient) {}

    create(annex: IAnnex): Observable<EntityResponseType> {
        return this.http.post<IAnnex>(this.resourceUrl, annex, { observe: 'response' });
    }

    update(annex: IAnnex): Observable<EntityResponseType> {
        return this.http.put<IAnnex>(this.resourceUrl, annex, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAnnex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnnex[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
