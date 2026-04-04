import { Odeljenje } from './odeljenje';
import { Dijagnoza } from './dijagnoza';

export class Pacijent {
  id!: number;
  ime!: String;
  prezime!: String;
  zdr_osiguranje!: boolean;
  datum_rodjenja!: String;
  odeljenje!: Odeljenje;
  dijagnoza!: Dijagnoza;
}