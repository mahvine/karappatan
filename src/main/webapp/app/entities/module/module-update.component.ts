import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IModule } from 'app/shared/model/module.model';
import { IQuestion } from 'app/shared/model/question.model';
import { ModuleService } from './module.service';
import { QuestionService } from '../question';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-module-update',
    templateUrl: './module-update.component.html'
})
export class ModuleUpdateComponent implements OnInit {
    module: IModule;
    isSaving: boolean;
    questions: IQuestion[];

    constructor(
        protected moduleService: ModuleService,
        protected questionService: QuestionService,
        protected activatedRoute: ActivatedRoute,
        protected jhiAlertService: JhiAlertService
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
}
