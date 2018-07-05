export interface IUtente {
    id?: number;
    username?: string;
    password?: string;
}

export class Utente implements IUtente {
    constructor(public id?: number, public username?: string, public password?: string) {}
}
