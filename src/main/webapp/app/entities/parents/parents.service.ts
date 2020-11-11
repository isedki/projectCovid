import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IParents } from 'app/shared/model/parents.model';

type EntityResponseType = HttpResponse<IParents>;
type EntityArrayResponseType = HttpResponse<IParents[]>;

@Injectable({ providedIn: 'root' })
export class ParentsService {
  public resourceUrl = SERVER_API_URL + 'api/parents';

  constructor(protected http: HttpClient) {}

  create(parents: IParents): Observable<EntityResponseType> {
    return this.http.post<IParents>(this.resourceUrl, parents, { observe: 'response' });
  }

  update(parents: IParents): Observable<EntityResponseType> {
    return this.http.put<IParents>(this.resourceUrl, parents, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParents>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParents[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
