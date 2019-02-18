/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KarappatanTestModule } from '../../../test.module';
import { RecommendationDeleteDialogComponent } from 'app/entities/recommendation/recommendation-delete-dialog.component';
import { RecommendationService } from 'app/entities/recommendation/recommendation.service';

describe('Component Tests', () => {
    describe('Recommendation Management Delete Component', () => {
        let comp: RecommendationDeleteDialogComponent;
        let fixture: ComponentFixture<RecommendationDeleteDialogComponent>;
        let service: RecommendationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KarappatanTestModule],
                declarations: [RecommendationDeleteDialogComponent]
            })
                .overrideTemplate(RecommendationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecommendationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecommendationService);
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
