import { Routes } from '@angular/router';

import { caseSummaryRoute } from './';

const CMS_ROUTES = [caseSummaryRoute];

export const cmsState: Routes = [
    {
        path: '',
        children: CMS_ROUTES
    }
];
