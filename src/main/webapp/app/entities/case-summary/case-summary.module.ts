import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KarappatanSharedModule } from 'app/shared';
import {
    CaseSummaryComponent,
    CaseSummaryDetailComponent,
    CaseSummaryUpdateComponent,
    CaseSummaryDeletePopupComponent,
    CaseSummaryDeleteDialogComponent,
    caseSummaryRoute,
    caseSummaryPopupRoute
} from './';

const ENTITY_STATES = [...caseSummaryRoute, ...caseSummaryPopupRoute];

@NgModule({
    imports: [KarappatanSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CaseSummaryComponent,
        CaseSummaryDetailComponent,
        CaseSummaryUpdateComponent,
        CaseSummaryDeleteDialogComponent,
        CaseSummaryDeletePopupComponent
    ],
    entryComponents: [CaseSummaryComponent, CaseSummaryUpdateComponent, CaseSummaryDeleteDialogComponent, CaseSummaryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarappatanCaseSummaryModule {}
