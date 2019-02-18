import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAnnex } from 'app/shared/model/annex.model';

@Component({
    selector: 'jhi-annex-detail',
    templateUrl: './annex-detail.component.html'
})
export class AnnexDetailComponent implements OnInit {
    annex: IAnnex;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
