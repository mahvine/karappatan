/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KarappatanTestModule } from '../../../test.module';
import { CaseSummaryOfferDeleteDialogComponent } from 'app/entities/case-summary-offer/case-summary-offer-delete-dialog.component';
import { CaseSummaryOfferService } from 'app/entities/case-summary-offer/case-summary-offer.service';

describe('Component Tests', () => {
    describe('CaseSummaryOffer Management Delete Component', () => {
        let comp: CaseSummaryOfferDeleteDialogComponent;
        let fixture: ComponentFixture<CaseSummaryOfferDeleteDialogComponent>;
        let service: CaseSummaryOfferService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KarappatanTestModule],
                declarations: [CaseSummaryOfferDeleteDialogComponent]
            })
                .overrideTemplate(CaseSummaryOfferDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CaseSummaryOfferDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaseSummaryOfferService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
