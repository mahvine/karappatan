import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRecommendation } from 'app/shared/model/recommendation.model';

@Component({
    selector: 'jhi-recommendation-detail',
    templateUrl: './recommendation-detail.component.html'
})
export class RecommendationDetailComponent implements OnInit {
    recommendation: IRecommendation;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recommendation }) => {
            this.recommendation = recommendation;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
