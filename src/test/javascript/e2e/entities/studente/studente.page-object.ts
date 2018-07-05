import { element, by, promise, ElementFinder } from 'protractor';

export class StudenteComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-studente div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class StudenteUpdatePage {
    pageTitle = element(by.id('jhi-studente-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomeInput = element(by.id('field_nome'));
    cognomeInput = element(by.id('field_cognome'));
    etaInput = element(by.id('field_eta'));
    idSelect = element(by.id('field_id'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNomeInput(nome): promise.Promise<void> {
        return this.nomeInput.sendKeys(nome);
    }

    getNomeInput() {
        return this.nomeInput.getAttribute('value');
    }

    setCognomeInput(cognome): promise.Promise<void> {
        return this.cognomeInput.sendKeys(cognome);
    }

    getCognomeInput() {
        return this.cognomeInput.getAttribute('value');
    }

    setEtaInput(eta): promise.Promise<void> {
        return this.etaInput.sendKeys(eta);
    }

    getEtaInput() {
        return this.etaInput.getAttribute('value');
    }

    idSelectLastOption(): promise.Promise<void> {
        return this.idSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    idSelectOption(option): promise.Promise<void> {
        return this.idSelect.sendKeys(option);
    }

    getIdSelect(): ElementFinder {
        return this.idSelect;
    }

    getIdSelectedOption() {
        return this.idSelect.element(by.css('option:checked')).getText();
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
