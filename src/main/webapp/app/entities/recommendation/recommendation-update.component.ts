import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IRecommendation } from 'app/shared/model/recommendation.model';
import { RecommendationService } from './recommendation.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module';

@Component({
    selector: 'jhi-recommendation-update',
    templateUrl: './recommendation-update.component.html'
})
export class RecommendationUpdateComponent implements OnInit {
    recommendation: IRecommendation;
    isSaving: boolean;

    recommendations: IRecommendation[];

    questions: IQuestion[];

    modules: IModule[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected recommendationService: RecommendationService,
        protected questionService: QuestionService,
        protected moduleService: ModuleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recommendation }) => {
            this.recommendation = recommendation;
        });
        this.recommendationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRecommendation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRecommendation[]>) => response.body)
            )
            .subscribe((res: IRecommendation[]) => (this.recommendations = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.questionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IQuestion[]>) => mayBeOk.ok),
                map((response: HttpResponse<IQuestion[]>) => response.body)
            )
            .subscribe((res: IQuestion[]) => (this.questions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.moduleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IModule[]>) => mayBeOk.ok),
                map((response: HttpResponse<IModule[]>) => response.body)
            )
            .subscribe((res: IModule[]) => (this.modules = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRecommendationById(index: number, item: IRecommendation) {
        return item.id;
    }

    trackQuestionById(index: number, item: IQuestion) {
        return item.id;
    }

    trackModuleById(index: number, item: IModule) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
