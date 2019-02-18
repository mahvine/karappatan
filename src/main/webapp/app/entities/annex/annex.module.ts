import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KarappatanSharedModule } from 'app/shared';
import {
    AnnexComponent,
    AnnexDetailComponent,
    AnnexUpdateComponent,
    AnnexDeletePopupComponent,
    AnnexDeleteDialogComponent,
    annexRoute,
    annexPopupRoute
} from './';

const ENTITY_STATES = [...annexRoute, ...annexPopupRoute];

@NgModule({
    imports: [KarappatanSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AnnexComponent, AnnexDetailComponent, AnnexUpdateComponent, AnnexDeleteDialogComponent, AnnexDeletePopupComponent],
    entryComponents: [AnnexComponent, AnnexUpdateComponent, AnnexDeleteDialogComponent, AnnexDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarappatanAnnexModule {}
