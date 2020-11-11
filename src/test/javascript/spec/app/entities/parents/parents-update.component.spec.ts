import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Version1TestModule } from '../../../test.module';
import { ParentsUpdateComponent } from 'app/entities/parents/parents-update.component';
import { ParentsService } from 'app/entities/parents/parents.service';
import { Parents } from 'app/shared/model/parents.model';

describe('Component Tests', () => {
  describe('Parents Management Update Component', () => {
    let comp: ParentsUpdateComponent;
    let fixture: ComponentFixture<ParentsUpdateComponent>;
    let service: ParentsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Version1TestModule],
        declarations: [ParentsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ParentsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParentsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParentsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Parents(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Parents();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
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
