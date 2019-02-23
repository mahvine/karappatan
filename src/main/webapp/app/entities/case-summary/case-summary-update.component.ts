import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICaseSummary } from 'app/shared/model/case-summary.model';
import { CaseSummaryService } from './case-summary.service';
import { IUser, UserService } from 'app/core';
import { IAnswer } from 'app/shared/model/answer.model';
import { AnswerService } from 'app/entities/answer';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module';

@Component({
    selector: 'jhi-case-summary-update',
    templateUrl: './case-summary-update.component.html'
})
export class CaseSummaryUpdateComponent implements OnInit {
    caseSummary: ICaseSummary;
    isSaving: boolean;

    users: IUser[];

    answers: IAnswer[];

    modules: IModule[];
    dateCreated: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected caseSummaryService: CaseSummaryService,
        protected userService: UserService,
        protected answerService: AnswerService,
        protected moduleService: ModuleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ caseSummary }) => {
            this.caseSummary = caseSummary;
            this.dateCreated = this.caseSummary.dateCreated != null ? this.caseSummary.dateCreated.format(DATE_TIME_FORMAT) : null;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.answerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAnswer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAnswer[]>) => response.body)
            )
            .subscribe((res: IAnswer[]) => (this.answers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.moduleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IModule[]>) => mayBeOk.ok),
                map((response: HttpResponse<IModule[]>) => response.body)
            )
            .subscribe((res: IModule[]) => (this.modules = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.caseSummary.dateCreated = this.dateCreated != null ? moment(this.dateCreated, DATE_TIME_FORMAT) : null;
        if (this.caseSummary.id !== undefined) {
            this.subscribeToSaveResponse(this.caseSummaryService.update(this.caseSummary));
        } else {
            this.subscribeToSaveResponse(this.caseSummaryService.create(this.caseSummary));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaseSummary>>) {
        result.subscribe((res: HttpResponse<ICaseSummary>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackAnswerById(index: number, item: IAnswer) {
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
