/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KarappatanTestModule } from '../../../test.module';
import { CaseSummaryDeleteDialogComponent } from 'app/entities/case-summary/case-summary-delete-dialog.component';
import { CaseSummaryService } from 'app/entities/case-summary/case-summary.service';

describe('Component Tests', () => {
    describe('CaseSummary Management Delete Component', () => {
        let comp: CaseSummaryDeleteDialogComponent;
        let fixture: ComponentFixture<CaseSummaryDeleteDialogComponent>;
        let service: CaseSummaryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KarappatanTestModule],
                declarations: [CaseSummaryDeleteDialogComponent]
            })
                .overrideTemplate(CaseSummaryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CaseSummaryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaseSummaryService);
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
