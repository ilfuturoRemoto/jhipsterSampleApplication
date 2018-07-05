import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUtente } from 'app/shared/model/utente.model';
import { UtenteService } from './utente.service';

@Component({
    selector: 'jhi-utente-update',
    templateUrl: './utente-update.component.html'
})
export class UtenteUpdateComponent implements OnInit {
    private _utente: IUtente;
    isSaving: boolean;

    constructor(private utenteService: UtenteService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ utente }) => {
            this.utente = utente;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.utente.id !== undefined) {
            this.subscribeToSaveResponse(this.utenteService.update(this.utente));
        } else {
            this.subscribeToSaveResponse(this.utenteService.create(this.utente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUtente>>) {
        result.subscribe((res: HttpResponse<IUtente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get utente() {
        return this._utente;
    }

    set utente(utente: IUtente) {
        this._utente = utente;
    }
}
