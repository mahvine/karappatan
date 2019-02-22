import { Routes } from '@angular/router';

import { casesummaryRoute } from './';

const CMS_ROUTES = [casesummaryRoute];

export const cmsState: Routes = [
    {
        path: '',
        children: CMS_ROUTES
    }
];
