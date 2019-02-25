/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KarappatanTestModule } from '../../../test.module';
import { CaseSummaryUpdateComponent } from 'app/entities/case-summary/case-summary-update.component';
import { CaseSummaryService } from 'app/entities/case-summary/case-summary.service';
import { CaseSummary } from 'app/shared/model/case-summary.model';

describe('Component Tests', () => {
    describe('CaseSummary Management Update Component', () => {
        let comp: CaseSummaryUpdateComponent;
        let fixture: ComponentFixture<CaseSummaryUpdateComponent>;
        let service: CaseSummaryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KarappatanTestModule],
                declarations: [CaseSummaryUpdateComponent]
            })
                .overrideTemplate(CaseSummaryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CaseSummaryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaseSummaryService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CaseSummary(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.caseSummary = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CaseSummary();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.caseSummary = entity;
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
