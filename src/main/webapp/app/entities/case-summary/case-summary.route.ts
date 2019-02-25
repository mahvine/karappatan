import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CaseSummary } from 'app/shared/model/case-summary.model';
import { CaseSummaryService } from './case-summary.service';
import { CaseSummaryComponent } from './case-summary.component';
import { CaseSummaryDetailComponent } from './case-summary-detail.component';
import { CaseSummaryUpdateComponent } from './case-summary-update.component';
import { CaseSummaryDeletePopupComponent } from './case-summary-delete-dialog.component';
import { ICaseSummary } from 'app/shared/model/case-summary.model';

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

export const caseSummaryRoute: Routes = [
    {
        path: '',
        component: CaseSummaryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CaseSummaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CaseSummaryDetailComponent,
        resolve: {
            caseSummary: CaseSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaseSummaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CaseSummaryUpdateComponent,
        resolve: {
            caseSummary: CaseSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaseSummaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CaseSummaryUpdateComponent,
        resolve: {
            caseSummary: CaseSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaseSummaries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const caseSummaryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CaseSummaryDeletePopupComponent,
        resolve: {
            caseSummary: CaseSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaseSummaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
