import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICaseSummaryOffer } from 'app/shared/model/case-summary-offer.model';
import { CaseSummaryOfferService } from './case-summary-offer.service';
import { ICaseSummary } from 'app/shared/model/case-summary.model';
import { CaseSummaryService } from 'app/entities/case-summary';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-case-summary-offer-update',
    templateUrl: './case-summary-offer-update.component.html'
})
export class CaseSummaryOfferUpdateComponent implements OnInit {
    caseSummaryOffer: ICaseSummaryOffer;
    isSaving: boolean;

    casesummaries: ICaseSummary[];

    users: IUser[];
    dateCreated: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected caseSummaryOfferService: CaseSummaryOfferService,
        protected caseSummaryService: CaseSummaryService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ caseSummaryOffer }) => {
            this.caseSummaryOffer = caseSummaryOffer;
            this.dateCreated =
                this.caseSummaryOffer.dateCreated != null ? this.caseSummaryOffer.dateCreated.format(DATE_TIME_FORMAT) : null;
        });
        this.caseSummaryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICaseSummary[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICaseSummary[]>) => response.body)
            )
            .subscribe((res: ICaseSummary[]) => (this.casesummaries = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.caseSummaryOffer.dateCreated = this.dateCreated != null ? moment(this.dateCreated, DATE_TIME_FORMAT) : null;
        if (this.caseSummaryOffer.id !== undefined) {
            this.subscribeToSaveResponse(this.caseSummaryOfferService.update(this.caseSummaryOffer));
        } else {
            this.subscribeToSaveResponse(this.caseSummaryOfferService.create(this.caseSummaryOffer));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaseSummaryOffer>>) {
        result.subscribe((res: HttpResponse<ICaseSummaryOffer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCaseSummaryById(index: number, item: ICaseSummary) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
