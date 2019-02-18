import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Recommendation } from 'app/shared/model/recommendation.model';
import { RecommendationService } from './recommendation.service';
import { RecommendationComponent } from './recommendation.component';
import { RecommendationDetailComponent } from './recommendation-detail.component';
import { RecommendationUpdateComponent } from './recommendation-update.component';
import { RecommendationDeletePopupComponent } from './recommendation-delete-dialog.component';
import { IRecommendation } from 'app/shared/model/recommendation.model';

@Injectable({ providedIn: 'root' })
export class RecommendationResolve implements Resolve<IRecommendation> {
    constructor(private service: RecommendationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRecommendation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Recommendation>) => response.ok),
                map((recommendation: HttpResponse<Recommendation>) => recommendation.body)
            );
        }
        return of(new Recommendation());
    }
}

export const recommendationRoute: Routes = [
    {
        path: '',
        component: RecommendationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Recommendations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RecommendationDetailComponent,
        resolve: {
            recommendation: RecommendationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recommendations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RecommendationUpdateComponent,
        resolve: {
            recommendation: RecommendationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recommendations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RecommendationUpdateComponent,
        resolve: {
            recommendation: RecommendationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recommendations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recommendationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RecommendationDeletePopupComponent,
        resolve: {
            recommendation: RecommendationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recommendations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
