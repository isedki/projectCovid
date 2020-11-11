import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Version1TestModule } from '../../../test.module';
import { CovidUpdatesDetailComponent } from 'app/entities/covid-updates/covid-updates-detail.component';
import { CovidUpdates } from 'app/shared/model/covid-updates.model';

describe('Component Tests', () => {
  describe('CovidUpdates Management Detail Component', () => {
    let comp: CovidUpdatesDetailComponent;
    let fixture: ComponentFixture<CovidUpdatesDetailComponent>;
    const route = ({ data: of({ covidUpdates: new CovidUpdates(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Version1TestModule],
        declarations: [CovidUpdatesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CovidUpdatesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CovidUpdatesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load covidUpdates on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.covidUpdates).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
