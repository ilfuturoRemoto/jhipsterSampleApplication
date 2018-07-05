import { IUtente } from 'app/shared/model//utente.model';

export interface IStudente {
    id?: number;
    nome?: string;
    cognome?: string;
    eta?: number;
    id?: IUtente;
}

export class Studente implements IStudente {
    constructor(public id?: number, public nome?: string, public cognome?: string, public eta?: number, public id?: IUtente) {}
}
