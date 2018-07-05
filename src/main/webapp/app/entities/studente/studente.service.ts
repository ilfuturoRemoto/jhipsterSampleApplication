import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudente } from 'app/shared/model/studente.model';

type EntityResponseType = HttpResponse<IStudente>;
type EntityArrayResponseType = HttpResponse<IStudente[]>;

@Injectable({ providedIn: 'root' })
export class StudenteService {
    private resourceUrl = SERVER_API_URL + 'api/studentes';

    constructor(private http: HttpClient) {}

    create(studente: IStudente): Observable<EntityResponseType> {
        return this.http.post<IStudente>(this.resourceUrl, studente, { observe: 'response' });
    }

    update(studente: IStudente): Observable<EntityResponseType> {
        return this.http.put<IStudente>(this.resourceUrl, studente, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStudente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStudente[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
