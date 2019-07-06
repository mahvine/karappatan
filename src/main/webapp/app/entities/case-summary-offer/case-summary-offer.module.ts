import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KarappatanSharedModule } from 'app/shared';
import {
    CaseSummaryOfferComponent,
    CaseSummaryOfferDetailComponent,
    CaseSummaryOfferUpdateComponent,
    CaseSummaryOfferDeletePopupComponent,
    CaseSummaryOfferDeleteDialogComponent,
    caseSummaryOfferRoute,
    caseSummaryOfferPopupRoute
} from './';

const ENTITY_STATES = [...caseSummaryOfferRoute, ...caseSummaryOfferPopupRoute];

@NgModule({
    imports: [KarappatanSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CaseSummaryOfferComponent,
        CaseSummaryOfferDetailComponent,
        CaseSummaryOfferUpdateComponent,
        CaseSummaryOfferDeleteDialogComponent,
        CaseSummaryOfferDeletePopupComponent
    ],
    entryComponents: [
        CaseSummaryOfferComponent,
        CaseSummaryOfferUpdateComponent,
        CaseSummaryOfferDeleteDialogComponent,
        CaseSummaryOfferDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarappatanCaseSummaryOfferModule {}
