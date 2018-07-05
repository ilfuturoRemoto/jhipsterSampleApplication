import { element, by, promise, ElementFinder } from 'protractor';

export class UtenteComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-utente div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UtenteUpdatePage {
    pageTitle = element(by.id('jhi-utente-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    usernameInput = element(by.id('field_username'));
    passwordInput = element(by.id('field_password'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setUsernameInput(username): promise.Promise<void> {
        return this.usernameInput.sendKeys(username);
    }

    getUsernameInput() {
        return this.usernameInput.getAttribute('value');
    }

    setPasswordInput(password): promise.Promise<void> {
        return this.passwordInput.sendKeys(password);
    }

    getPasswordInput() {
        return this.passwordInput.getAttribute('value');
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
