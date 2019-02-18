import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Annex } from 'app/shared/model/annex.model';
import { AnnexService } from './annex.service';
import { AnnexComponent } from './annex.component';
import { AnnexDetailComponent } from './annex-detail.component';
import { AnnexUpdateComponent } from './annex-update.component';
import { AnnexDeletePopupComponent } from './annex-delete-dialog.component';
import { IAnnex } from 'app/shared/model/annex.model';

@Injectable({ providedIn: 'root' })
export class AnnexResolve implements Resolve<IAnnex> {
    constructor(private service: AnnexService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnnex> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Annex>) => response.ok),
                map((annex: HttpResponse<Annex>) => annex.body)
            );
        }
        return of(new Annex());
    }
}

export const annexRoute: Routes = [
    {
        path: '',
        component: AnnexComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Annexes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AnnexDetailComponent,
        resolve: {
            annex: AnnexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Annexes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AnnexUpdateComponent,
        resolve: {
            annex: AnnexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Annexes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AnnexUpdateComponent,
        resolve: {
            annex: AnnexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Annexes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const annexPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AnnexDeletePopupComponent,
        resolve: {
            annex: AnnexResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Annexes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
