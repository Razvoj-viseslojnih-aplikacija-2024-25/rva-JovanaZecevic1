import { Bolnica } from './bolnica';

export class Odeljenje {
  id!: number;
  naziv!: String;
  lokacija!: String;
  bolnica!: Bolnica;
}