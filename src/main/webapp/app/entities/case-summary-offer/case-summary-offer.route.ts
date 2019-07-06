import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CaseSummaryOffer } from 'app/shared/model/case-summary-offer.model';
import { CaseSummaryOfferService } from './case-summary-offer.service';
import { CaseSummaryOfferComponent } from './case-summary-offer.component';
import { CaseSummaryOfferDetailComponent } from './case-summary-offer-detail.component';
import { CaseSummaryOfferUpdateComponent } from './case-summary-offer-update.component';
import { CaseSummaryOfferDeletePopupComponent } from './case-summary-offer-delete-dialog.component';
import { ICaseSummaryOffer } from 'app/shared/model/case-summary-offer.model';

@Injectable({ providedIn: 'root' })
export class CaseSummaryOfferResolve implements Resolve<ICaseSummaryOffer> {
    constructor(private service: CaseSummaryOfferService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICaseSummaryOffer> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CaseSummaryOffer>) => response.ok),
                map((caseSummaryOffer: HttpResponse<CaseSummaryOffer>) => caseSummaryOffer.body)
            );
        }
        return of(new CaseSummaryOffer());
    }
}

export const caseSummaryOfferRoute: Routes = [
    {
        path: '',
        component: CaseSummaryOfferComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'CaseSummaryOffers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CaseSummaryOfferDetailComponent,
        resolve: {
            caseSummaryOffer: CaseSummaryOfferResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaseSummaryOffers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CaseSummaryOfferUpdateComponent,
        resolve: {
            caseSummaryOffer: CaseSummaryOfferResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaseSummaryOffers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CaseSummaryOfferUpdateComponent,
        resolve: {
            caseSummaryOffer: CaseSummaryOfferResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaseSummaryOffers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const caseSummaryOfferPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CaseSummaryOfferDeletePopupComponent,
        resolve: {
            caseSummaryOffer: CaseSummaryOfferResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CaseSummaryOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
