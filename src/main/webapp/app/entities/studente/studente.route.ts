import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Studente } from 'app/shared/model/studente.model';
import { StudenteService } from './studente.service';
import { StudenteComponent } from './studente.component';
import { StudenteDetailComponent } from './studente-detail.component';
import { StudenteUpdateComponent } from './studente-update.component';
import { StudenteDeletePopupComponent } from './studente-delete-dialog.component';
import { IStudente } from 'app/shared/model/studente.model';

@Injectable({ providedIn: 'root' })
export class StudenteResolve implements Resolve<IStudente> {
    constructor(private service: StudenteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((studente: HttpResponse<Studente>) => studente.body);
        }
        return Observable.of(new Studente());
    }
}

export const studenteRoute: Routes = [
    {
        path: 'studente',
        component: StudenteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.studente.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'studente/:id/view',
        component: StudenteDetailComponent,
        resolve: {
            studente: StudenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.studente.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'studente/new',
        component: StudenteUpdateComponent,
        resolve: {
            studente: StudenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.studente.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'studente/:id/edit',
        component: StudenteUpdateComponent,
        resolve: {
            studente: StudenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.studente.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentePopupRoute: Routes = [
    {
        path: 'studente/:id/delete',
        component: StudenteDeletePopupComponent,
        resolve: {
            studente: StudenteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.studente.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
