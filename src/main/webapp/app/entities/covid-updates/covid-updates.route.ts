import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICovidUpdates, CovidUpdates } from 'app/shared/model/covid-updates.model';
import { CovidUpdatesService } from './covid-updates.service';
import { CovidUpdatesComponent } from './covid-updates.component';
import { CovidUpdatesDetailComponent } from './covid-updates-detail.component';
import { CovidUpdatesUpdateComponent } from './covid-updates-update.component';

@Injectable({ providedIn: 'root' })
export class CovidUpdatesResolve implements Resolve<ICovidUpdates> {
  constructor(private service: CovidUpdatesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICovidUpdates> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((covidUpdates: HttpResponse<CovidUpdates>) => {
          if (covidUpdates.body) {
            return of(covidUpdates.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CovidUpdates());
  }
}

export const covidUpdatesRoute: Routes = [
  {
    path: '',
    component: CovidUpdatesComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'CovidUpdates',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CovidUpdatesDetailComponent,
    resolve: {
      covidUpdates: CovidUpdatesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CovidUpdates',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CovidUpdatesUpdateComponent,
    resolve: {
      covidUpdates: CovidUpdatesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CovidUpdates',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CovidUpdatesUpdateComponent,
    resolve: {
      covidUpdates: CovidUpdatesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CovidUpdates',
    },
    canActivate: [UserRouteAccessService],
  },
];
