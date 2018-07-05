import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    StudenteComponent,
    StudenteDetailComponent,
    StudenteUpdateComponent,
    StudenteDeletePopupComponent,
    StudenteDeleteDialogComponent,
    studenteRoute,
    studentePopupRoute
} from './';

const ENTITY_STATES = [...studenteRoute, ...studentePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StudenteComponent,
        StudenteDetailComponent,
        StudenteUpdateComponent,
        StudenteDeleteDialogComponent,
        StudenteDeletePopupComponent
    ],
    entryComponents: [StudenteComponent, StudenteUpdateComponent, StudenteDeleteDialogComponent, StudenteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationStudenteModule {}
