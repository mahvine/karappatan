import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICaseSummary } from 'app/shared/model/case-summary.model';
import { CaseSummaryService } from './case-summary.service';

@Component({
    selector: 'jhi-case-summary-delete-dialog',
    templateUrl: './case-summary-delete-dialog.component.html'
})
export class CaseSummaryDeleteDialogComponent {
    caseSummary: ICaseSummary;

    constructor(
        protected caseSummaryService: CaseSummaryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.caseSummaryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'caseSummaryListModification',
                content: 'Deleted an caseSummary'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-case-summary-delete-popup',
    template: ''
})
export class CaseSummaryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ caseSummary }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CaseSummaryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.caseSummary = caseSummary;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/case-summary', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/case-summary', { outlets: { popup: null } }]);
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
