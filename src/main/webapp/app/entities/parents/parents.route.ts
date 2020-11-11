import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IParents, Parents } from 'app/shared/model/parents.model';
import { ParentsService } from './parents.service';
import { ParentsComponent } from './parents.component';
import { ParentsDetailComponent } from './parents-detail.component';
import { ParentsUpdateComponent } from './parents-update.component';

@Injectable({ providedIn: 'root' })
export class ParentsResolve implements Resolve<IParents> {
  constructor(private service: ParentsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParents> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((parents: HttpResponse<Parents>) => {
          if (parents.body) {
            return of(parents.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Parents());
  }
}

export const parentsRoute: Routes = [
  {
    path: '',
    component: ParentsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Parents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParentsDetailComponent,
    resolve: {
      parents: ParentsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Parents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParentsUpdateComponent,
    resolve: {
      parents: ParentsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Parents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParentsUpdateComponent,
    resolve: {
      parents: ParentsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Parents',
    },
    canActivate: [UserRouteAccessService],
  },
];
