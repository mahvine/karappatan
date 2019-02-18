import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiDataUtils } from 'ng-jhipster';
import { IAnnex } from 'app/shared/model/annex.model';
import { AnnexService } from './annex.service';

@Component({
    selector: 'jhi-annex-update',
    templateUrl: './annex-update.component.html'
})
export class AnnexUpdateComponent implements OnInit {
    annex: IAnnex;
    isSaving: boolean;

    constructor(protected dataUtils: JhiDataUtils, protected annexService: AnnexService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ annex }) => {
            this.annex = annex;
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
        if (this.annex.id !== undefined) {
            this.subscribeToSaveResponse(this.annexService.update(this.annex));
        } else {
            this.subscribeToSaveResponse(this.annexService.create(this.annex));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnnex>>) {
        result.subscribe((res: HttpResponse<IAnnex>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
