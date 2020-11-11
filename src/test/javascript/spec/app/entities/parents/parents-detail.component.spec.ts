import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Version1TestModule } from '../../../test.module';
import { ParentsDetailComponent } from 'app/entities/parents/parents-detail.component';
import { Parents } from 'app/shared/model/parents.model';

describe('Component Tests', () => {
  describe('Parents Management Detail Component', () => {
    let comp: ParentsDetailComponent;
    let fixture: ComponentFixture<ParentsDetailComponent>;
    const route = ({ data: of({ parents: new Parents(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Version1TestModule],
        declarations: [ParentsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ParentsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParentsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load parents on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parents).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
