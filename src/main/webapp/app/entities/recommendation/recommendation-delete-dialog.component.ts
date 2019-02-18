import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecommendation } from 'app/shared/model/recommendation.model';
import { RecommendationService } from './recommendation.service';

@Component({
    selector: 'jhi-recommendation-delete-dialog',
    templateUrl: './recommendation-delete-dialog.component.html'
})
export class RecommendationDeleteDialogComponent {
    recommendation: IRecommendation;

    constructor(
        protected recommendationService: RecommendationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recommendationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recommendationListModification',
                content: 'Deleted an recommendation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recommendation-delete-popup',
    template: ''
})
export class RecommendationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recommendation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RecommendationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.recommendation = recommendation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/recommendation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/recommendation', { outlets: { popup: null } }]);
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
