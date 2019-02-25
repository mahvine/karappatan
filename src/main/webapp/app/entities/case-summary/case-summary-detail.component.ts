import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICaseSummary } from 'app/shared/model/case-summary.model';

@Component({
    selector: 'jhi-case-summary-detail',
    templateUrl: './case-summary-detail.component.html'
})
export class CaseSummaryDetailComponent implements OnInit {
    caseSummary: ICaseSummary;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ caseSummary }) => {
            this.caseSummary = caseSummary;
        });
    }

    previousState() {
        window.history.back();
    }
}
