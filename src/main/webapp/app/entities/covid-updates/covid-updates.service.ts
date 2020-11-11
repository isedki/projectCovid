import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICovidUpdates } from 'app/shared/model/covid-updates.model';

type EntityResponseType = HttpResponse<ICovidUpdates>;
type EntityArrayResponseType = HttpResponse<ICovidUpdates[]>;

@Injectable({ providedIn: 'root' })
export class CovidUpdatesService {
  public resourceUrl = SERVER_API_URL + 'api/covid-updates';

  constructor(protected http: HttpClient) {}

  create(covidUpdates: ICovidUpdates): Observable<EntityResponseType> {
    return this.http.post<ICovidUpdates>(this.resourceUrl, covidUpdates, { observe: 'response' });
  }

  update(covidUpdates: ICovidUpdates): Observable<EntityResponseType> {
    return this.http.put<ICovidUpdates>(this.resourceUrl, covidUpdates, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICovidUpdates>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICovidUpdates[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
