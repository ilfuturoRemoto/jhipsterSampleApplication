import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { UtenteComponentsPage, UtenteUpdatePage } from './utente.page-object';

describe('Utente e2e test', () => {
    let navBarPage: NavBarPage;
    let utenteUpdatePage: UtenteUpdatePage;
    let utenteComponentsPage: UtenteComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Utentes', () => {
        navBarPage.goToEntity('utente');
        utenteComponentsPage = new UtenteComponentsPage();
        expect(utenteComponentsPage.getTitle()).toMatch(/jhipsterSampleApplicationApp.utente.home.title/);
    });

    it('should load create Utente page', () => {
        utenteComponentsPage.clickOnCreateButton();
        utenteUpdatePage = new UtenteUpdatePage();
        expect(utenteUpdatePage.getPageTitle()).toMatch(/jhipsterSampleApplicationApp.utente.home.createOrEditLabel/);
        utenteUpdatePage.cancel();
    });

    it('should create and save Utentes', () => {
        utenteComponentsPage.clickOnCreateButton();
        utenteUpdatePage.setUsernameInput('username');
        expect(utenteUpdatePage.getUsernameInput()).toMatch('username');
        utenteUpdatePage.setPasswordInput('password');
        expect(utenteUpdatePage.getPasswordInput()).toMatch('password');
        utenteUpdatePage.save();
        expect(utenteUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
