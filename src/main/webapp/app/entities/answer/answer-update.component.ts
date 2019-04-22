import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAnswer } from 'app/shared/model/answer.model';
import { AnswerService } from './answer.service';
import { IAnnex } from 'app/shared/model/annex.model';
import { AnnexService } from 'app/entities/annex';
import { IRecommendation } from 'app/shared/model/recommendation.model';
import { RecommendationService } from 'app/entities/recommendation';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';

@Component({
    selector: 'jhi-answer-update',
    templateUrl: './answer-update.component.html'
})
export class AnswerUpdateComponent implements OnInit {
    answer: IAnswer;
    isSaving: boolean;

    annexes: IAnnex[];

    recommendations: IRecommendation[];

    questions: IQuestion[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected answerService: AnswerService,
        protected annexService: AnnexService,
        protected recommendationService: RecommendationService,
        protected questionService: QuestionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ answer }) => {
            this.answer = answer;
        });
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
        this.questionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IQuestion[]>) => mayBeOk.ok),
                map((response: HttpResponse<IQuestion[]>) => response.body)
            )
            .subscribe((res: IQuestion[]) => (this.questions = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.answer.id !== undefined) {
            this.subscribeToSaveResponse(this.answerService.update(this.answer));
        } else {
            this.subscribeToSaveResponse(this.answerService.create(this.answer));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswer>>) {
        result.subscribe((res: HttpResponse<IAnswer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAnnexById(index: number, item: IAnnex) {
        return item.id;
    }

    trackRecommendationById(index: number, item: IRecommendation) {
        return item.id;
    }

    trackQuestionById(index: number, item: IQuestion) {
        return item.id;
    }
}
