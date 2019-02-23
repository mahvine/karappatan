import { Route } from '@angular/router';

import { CaseSummaryComponent } from './case-summary.component';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';

export const caseSummaryRoute: Route = {
    path: 'casesummaries',
    component: CaseSummaryComponent,
    resolve: {
        pagingParams: JhiResolvePagingParams
    },
    data: {
        authorities: [],
        pageTitle: 'Case Summaries'
    }
};
