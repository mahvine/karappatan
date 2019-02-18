import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnnex } from 'app/shared/model/annex.model';
import { AnnexService } from './annex.service';

@Component({
    selector: 'jhi-annex-delete-dialog',
    templateUrl: './annex-delete-dialog.component.html'
})
export class AnnexDeleteDialogComponent {
    annex: IAnnex;

    constructor(protected annexService: AnnexService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.annexService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'annexListModification',
                content: 'Deleted an annex'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-annex-delete-popup',
    template: ''
})
export class AnnexDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ annex }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AnnexDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.annex = annex;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/annex', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/annex', { outlets: { popup: null } }]);
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
