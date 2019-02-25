import { Routes, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';

import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core';
import { CaseSummaryDetailComponent } from './case-summary-detail.component';
import { CaseSummaryService } from './case-summary.service';
import { CaseSummaryComponent } from './case-summary.component';

import { ICaseSummary, CaseSummary } from 'app/shared/model/case-summary.model';

@Injectable({ providedIn: 'root' })
export class CaseSummaryResolve implements Resolve<ICaseSummary> {
    constructor(private service: CaseSummaryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICaseSummary> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CaseSummary>) => response.ok),
                map((caseSummary: HttpResponse<CaseSummary>) => caseSummary.body)
            );
        }
        return of(new CaseSummary());
    }
}

export const caseSummaryRoutes: Routes = [
    {
        path: 'casesummaries',
        component: CaseSummaryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_IBP_USER', 'ROLE_ADMIN'],
            pageTitle: 'Case Summaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'casesummaries/:id/view',
        component: CaseSummaryDetailComponent,
        resolve: {
            caseSummary: CaseSummaryResolve
        },
        data: {
            authorities: ['ROLE_IBP_USER', 'ROLE_ADMIN'],
            pageTitle: 'Case Summaries'
        },
        canActivate: [UserRouteAccessService]
    }
];
