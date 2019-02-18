/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KarappatanTestModule } from '../../../test.module';
import { AnnexDeleteDialogComponent } from 'app/entities/annex/annex-delete-dialog.component';
import { AnnexService } from 'app/entities/annex/annex.service';

describe('Component Tests', () => {
    describe('Annex Management Delete Component', () => {
        let comp: AnnexDeleteDialogComponent;
        let fixture: ComponentFixture<AnnexDeleteDialogComponent>;
        let service: AnnexService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KarappatanTestModule],
                declarations: [AnnexDeleteDialogComponent]
            })
                .overrideTemplate(AnnexDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnnexDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnexService);
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
