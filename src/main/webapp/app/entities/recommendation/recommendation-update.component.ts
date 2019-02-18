import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiDataUtils } from 'ng-jhipster';
import { IRecommendation } from 'app/shared/model/recommendation.model';
import { RecommendationService } from './recommendation.service';

@Component({
    selector: 'jhi-recommendation-update',
    templateUrl: './recommendation-update.component.html'
})
export class RecommendationUpdateComponent implements OnInit {
    recommendation: IRecommendation;
    isSaving: boolean;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected recommendationService: RecommendationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recommendation.id !== undefined) {
            this.subscribeToSaveResponse(this.recommendationService.update(this.recommendation));
        } else {
            this.subscribeToSaveResponse(this.recommendationService.create(this.recommendation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecommendation>>) {
        result.subscribe((res: HttpResponse<IRecommendation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
