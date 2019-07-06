/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KarappatanTestModule } from '../../../test.module';
import { CaseSummaryOfferDetailComponent } from 'app/entities/case-summary-offer/case-summary-offer-detail.component';
import { CaseSummaryOffer } from 'app/shared/model/case-summary-offer.model';

describe('Component Tests', () => {
    describe('CaseSummaryOffer Management Detail Component', () => {
        let comp: CaseSummaryOfferDetailComponent;
        let fixture: ComponentFixture<CaseSummaryOfferDetailComponent>;
        const route = ({ data: of({ caseSummaryOffer: new CaseSummaryOffer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KarappatanTestModule],
                declarations: [CaseSummaryOfferDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CaseSummaryOfferDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CaseSummaryOfferDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.caseSummaryOffer).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
