import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    UtenteComponent,
    UtenteDetailComponent,
    UtenteUpdateComponent,
    UtenteDeletePopupComponent,
    UtenteDeleteDialogComponent,
    utenteRoute,
    utentePopupRoute
} from './';

const ENTITY_STATES = [...utenteRoute, ...utentePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [UtenteComponent, UtenteDetailComponent, UtenteUpdateComponent, UtenteDeleteDialogComponent, UtenteDeletePopupComponent],
    entryComponents: [UtenteComponent, UtenteUpdateComponent, UtenteDeleteDialogComponent, UtenteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationUtenteModule {}
