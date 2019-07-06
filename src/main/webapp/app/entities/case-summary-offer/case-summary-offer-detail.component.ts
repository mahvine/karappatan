import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICaseSummaryOffer } from 'app/shared/model/case-summary-offer.model';

@Component({
    selector: 'jhi-case-summary-offer-detail',
    templateUrl: './case-summary-offer-detail.component.html'
})
export class CaseSummaryOfferDetailComponent implements OnInit {
    caseSummaryOffer: ICaseSummaryOffer;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ caseSummaryOffer }) => {
            this.caseSummaryOffer = caseSummaryOffer;
        });
    }

    previousState() {
        window.history.back();
    }
}
