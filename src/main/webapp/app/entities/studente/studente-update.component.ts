import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStudente } from 'app/shared/model/studente.model';
import { StudenteService } from './studente.service';
import { IUtente } from 'app/shared/model/utente.model';
import { UtenteService } from 'app/entities/utente';

@Component({
    selector: 'jhi-studente-update',
    templateUrl: './studente-update.component.html'
})
export class StudenteUpdateComponent implements OnInit {
    private _studente: IStudente;
    isSaving: boolean;

    ids: IUtente[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private studenteService: StudenteService,
        private utenteService: UtenteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ studente }) => {
            this.studente = studente;
        });
        this.utenteService.query({ filter: 'studente-is-null' }).subscribe(
            (res: HttpResponse<IUtente[]>) => {
                if (!this.studente.id || !this.studente.id.id) {
                    this.ids = res.body;
                } else {
                    this.utenteService.find(this.studente.id.id).subscribe(
                        (subRes: HttpResponse<IUtente>) => {
                            this.ids = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.studente.id !== undefined) {
            this.subscribeToSaveResponse(this.studenteService.update(this.studente));
        } else {
            this.subscribeToSaveResponse(this.studenteService.create(this.studente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStudente>>) {
        result.subscribe((res: HttpResponse<IStudente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUtenteById(index: number, item: IUtente) {
        return item.id;
    }
    get studente() {
        return this._studente;
    }

    set studente(studente: IStudente) {
        this._studente = studente;
    }
}
