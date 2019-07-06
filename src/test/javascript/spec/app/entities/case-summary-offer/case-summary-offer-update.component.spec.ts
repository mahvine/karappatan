/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KarappatanTestModule } from '../../../test.module';
import { CaseSummaryOfferUpdateComponent } from 'app/entities/case-summary-offer/case-summary-offer-update.component';
import { CaseSummaryOfferService } from 'app/entities/case-summary-offer/case-summary-offer.service';
import { CaseSummaryOffer } from 'app/shared/model/case-summary-offer.model';

describe('Component Tests', () => {
    describe('CaseSummaryOffer Management Update Component', () => {
        let comp: CaseSummaryOfferUpdateComponent;
        let fixture: ComponentFixture<CaseSummaryOfferUpdateComponent>;
        let service: CaseSummaryOfferService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KarappatanTestModule],
                declarations: [CaseSummaryOfferUpdateComponent]
            })
                .overrideTemplate(CaseSummaryOfferUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CaseSummaryOfferUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaseSummaryOfferService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CaseSummaryOffer(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.caseSummaryOffer = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CaseSummaryOffer();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.caseSummaryOffer = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
