import { Route } from '@angular/router';

import { CasesummaryComponent } from './casesummary.component';

export const casesummaryRoute: Route = {
    path: 'casesummaries',
    component: CasesummaryComponent,
    data: {
        authorities: [],
        pageTitle: 'Case Summaries'
    }
};
