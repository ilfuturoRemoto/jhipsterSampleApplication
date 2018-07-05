import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStudente } from 'app/shared/model/studente.model';
import { Principal } from 'app/core';
import { StudenteService } from './studente.service';

@Component({
    selector: 'jhi-studente',
    templateUrl: './studente.component.html'
})
export class StudenteComponent implements OnInit, OnDestroy {
    studentes: IStudente[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private studenteService: StudenteService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.studenteService.query().subscribe(
            (res: HttpResponse<IStudente[]>) => {
                this.studentes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStudentes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStudente) {
        return item.id;
    }

    registerChangeInStudentes() {
        this.eventSubscriber = this.eventManager.subscribe('studenteListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
