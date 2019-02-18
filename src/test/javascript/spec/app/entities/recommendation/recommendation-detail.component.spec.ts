/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KarappatanTestModule } from '../../../test.module';
import { RecommendationDetailComponent } from 'app/entities/recommendation/recommendation-detail.component';
import { Recommendation } from 'app/shared/model/recommendation.model';

describe('Component Tests', () => {
    describe('Recommendation Management Detail Component', () => {
        let comp: RecommendationDetailComponent;
        let fixture: ComponentFixture<RecommendationDetailComponent>;
        const route = ({ data: of({ recommendation: new Recommendation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KarappatanTestModule],
                declarations: [RecommendationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RecommendationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecommendationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.recommendation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
