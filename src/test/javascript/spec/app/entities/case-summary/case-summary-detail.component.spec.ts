/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KarappatanTestModule } from '../../../test.module';
import { CaseSummaryDetailComponent } from 'app/entities/case-summary/case-summary-detail.component';
import { CaseSummary } from 'app/shared/model/case-summary.model';

describe('Component Tests', () => {
    describe('CaseSummary Management Detail Component', () => {
        let comp: CaseSummaryDetailComponent;
        let fixture: ComponentFixture<CaseSummaryDetailComponent>;
        const route = ({ data: of({ caseSummary: new CaseSummary(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KarappatanTestModule],
                declarations: [CaseSummaryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CaseSummaryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CaseSummaryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.caseSummary).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
