import { Routes } from '@angular/router';

import { caseSummaryRoutes } from './';

const CMS_ROUTES = caseSummaryRoutes;

export const cmsState: Routes = [
    {
        path: '',
        children: CMS_ROUTES
    }
];
