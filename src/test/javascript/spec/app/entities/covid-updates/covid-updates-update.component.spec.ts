import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Version1TestModule } from '../../../test.module';
import { CovidUpdatesUpdateComponent } from 'app/entities/covid-updates/covid-updates-update.component';
import { CovidUpdatesService } from 'app/entities/covid-updates/covid-updates.service';
import { CovidUpdates } from 'app/shared/model/covid-updates.model';

describe('Component Tests', () => {
  describe('CovidUpdates Management Update Component', () => {
    let comp: CovidUpdatesUpdateComponent;
    let fixture: ComponentFixture<CovidUpdatesUpdateComponent>;
    let service: CovidUpdatesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Version1TestModule],
        declarations: [CovidUpdatesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CovidUpdatesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CovidUpdatesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CovidUpdatesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CovidUpdates(123);
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
        const entity = new CovidUpdates();
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
