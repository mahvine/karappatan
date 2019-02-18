/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KarappatanTestModule } from '../../../test.module';
import { AnnexUpdateComponent } from 'app/entities/annex/annex-update.component';
import { AnnexService } from 'app/entities/annex/annex.service';
import { Annex } from 'app/shared/model/annex.model';

describe('Component Tests', () => {
    describe('Annex Management Update Component', () => {
        let comp: AnnexUpdateComponent;
        let fixture: ComponentFixture<AnnexUpdateComponent>;
        let service: AnnexService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KarappatanTestModule],
                declarations: [AnnexUpdateComponent]
            })
                .overrideTemplate(AnnexUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnnexUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnexService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Annex(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.annex = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Annex();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.annex = entity;
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
