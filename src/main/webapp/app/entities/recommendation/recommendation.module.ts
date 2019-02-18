import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KarappatanSharedModule } from 'app/shared';
import {
    RecommendationComponent,
    RecommendationDetailComponent,
    RecommendationUpdateComponent,
    RecommendationDeletePopupComponent,
    RecommendationDeleteDialogComponent,
    recommendationRoute,
    recommendationPopupRoute
} from './';

const ENTITY_STATES = [...recommendationRoute, ...recommendationPopupRoute];

@NgModule({
    imports: [KarappatanSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecommendationComponent,
        RecommendationDetailComponent,
        RecommendationUpdateComponent,
        RecommendationDeleteDialogComponent,
        RecommendationDeletePopupComponent
    ],
    entryComponents: [
        RecommendationComponent,
        RecommendationUpdateComponent,
        RecommendationDeleteDialogComponent,
        RecommendationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarappatanRecommendationModule {}
