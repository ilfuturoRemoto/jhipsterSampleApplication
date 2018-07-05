/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { StudenteComponent } from 'app/entities/studente/studente.component';
import { StudenteService } from 'app/entities/studente/studente.service';
import { Studente } from 'app/shared/model/studente.model';

describe('Component Tests', () => {
    describe('Studente Management Component', () => {
        let comp: StudenteComponent;
        let fixture: ComponentFixture<StudenteComponent>;
        let service: StudenteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [StudenteComponent],
                providers: []
            })
                .overrideTemplate(StudenteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StudenteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudenteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Studente(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.studentes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
