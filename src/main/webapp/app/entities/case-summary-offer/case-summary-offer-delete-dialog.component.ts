import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICaseSummaryOffer } from 'app/shared/model/case-summary-offer.model';
import { CaseSummaryOfferService } from './case-summary-offer.service';

@Component({
    selector: 'jhi-case-summary-offer-delete-dialog',
    templateUrl: './case-summary-offer-delete-dialog.component.html'
})
export class CaseSummaryOfferDeleteDialogComponent {
    caseSummaryOffer: ICaseSummaryOffer;

    constructor(
        protected caseSummaryOfferService: CaseSummaryOfferService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.caseSummaryOfferService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'caseSummaryOfferListModification',
                content: 'Deleted an caseSummaryOffer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-case-summary-offer-delete-popup',
    template: ''
})
export class CaseSummaryOfferDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ caseSummaryOffer }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CaseSummaryOfferDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.caseSummaryOffer = caseSummaryOffer;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/case-summary-offer', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/case-summary-offer', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
