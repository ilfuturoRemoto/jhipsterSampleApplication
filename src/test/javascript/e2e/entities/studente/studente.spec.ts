import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { StudenteComponentsPage, StudenteUpdatePage } from './studente.page-object';

describe('Studente e2e test', () => {
    let navBarPage: NavBarPage;
    let studenteUpdatePage: StudenteUpdatePage;
    let studenteComponentsPage: StudenteComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Studentes', () => {
        navBarPage.goToEntity('studente');
        studenteComponentsPage = new StudenteComponentsPage();
        expect(studenteComponentsPage.getTitle()).toMatch(/jhipsterSampleApplicationApp.studente.home.title/);
    });

    it('should load create Studente page', () => {
        studenteComponentsPage.clickOnCreateButton();
        studenteUpdatePage = new StudenteUpdatePage();
        expect(studenteUpdatePage.getPageTitle()).toMatch(/jhipsterSampleApplicationApp.studente.home.createOrEditLabel/);
        studenteUpdatePage.cancel();
    });

    it('should create and save Studentes', () => {
        studenteComponentsPage.clickOnCreateButton();
        studenteUpdatePage.setNomeInput('nome');
        expect(studenteUpdatePage.getNomeInput()).toMatch('nome');
        studenteUpdatePage.setCognomeInput('cognome');
        expect(studenteUpdatePage.getCognomeInput()).toMatch('cognome');
        studenteUpdatePage.setEtaInput('5');
        expect(studenteUpdatePage.getEtaInput()).toMatch('5');
        studenteUpdatePage.idSelectLastOption();
        studenteUpdatePage.save();
        expect(studenteUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
