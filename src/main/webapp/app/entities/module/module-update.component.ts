import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from './module.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';
import { IAnnex } from 'app/shared/model/annex.model';
import { AnnexService } from 'app/entities/annex';
import { IRecommendation } from 'app/shared/model/recommendation.model';
import { RecommendationService } from 'app/entities/recommendation';

@Component({
    selector: 'jhi-module-update',
    templateUrl: './module-update.component.html'
})
export class ModuleUpdateComponent implements OnInit {
    module: IModule;
    isSaving: boolean;

    questions: IQuestion[];

    annexes: IAnnex[];

    recommendations: IRecommendation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected moduleService: ModuleService,
        protected questionService: QuestionService,
        protected annexService: AnnexService,
        protected recommendationService: RecommendationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ module }) => {
            this.module = module;
        });
        this.questionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IQuestion[]>) => mayBeOk.ok),
                map((response: HttpResponse<IQuestion[]>) => response.body)
            )
            .subscribe((res: IQuestion[]) => (this.questions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.annexService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAnnex[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAnnex[]>) => response.body)
            )
            .subscribe((res: IAnnex[]) => (this.annexes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.recommendationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRecommendation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRecommendation[]>) => response.body)
            )
            .subscribe((res: IRecommendation[]) => (this.recommendations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.module.id !== undefined) {
            this.subscribeToSaveResponse(this.moduleService.update(this.module));
        } else {
            this.subscribeToSaveResponse(this.moduleService.create(this.module));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IModule>>) {
        result.subscribe((res: HttpResponse<IModule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackQuestionById(index: number, item: IQuestion) {
        return item.id;
    }

    trackAnnexById(index: number, item: IAnnex) {
        return item.id;
    }

    trackRecommendationById(index: number, item: IRecommendation) {
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
