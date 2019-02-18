/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KarappatanTestModule } from '../../../test.module';
import { AnnexDetailComponent } from 'app/entities/annex/annex-detail.component';
import { Annex } from 'app/shared/model/annex.model';

describe('Component Tests', () => {
    describe('Annex Management Detail Component', () => {
        let comp: AnnexDetailComponent;
        let fixture: ComponentFixture<AnnexDetailComponent>;
        const route = ({ data: of({ annex: new Annex(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KarappatanTestModule],
                declarations: [AnnexDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AnnexDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnnexDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.annex).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
