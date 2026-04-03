insert into bolnica(id, naziv, adresa, budzet)
values (nextval('bolnica_seq'), 'Klinički centar Vojvodine', 'Hajduk Veljkova 1, Novi Sad', 5000000),
       (nextval('bolnica_seq'), 'Opšta bolnica Beograd', 'Balkanska 5, Beograd', 2000000),
       (nextval('bolnica_seq'), 'Institut za onkologiju', 'Put doktora Goldmana 4, Sremska Kamenica', 3500000);

insert into odeljenje(id, naziv, lokacija, bolnica_id)
values (nextval('odeljenje_seq'), 'Kardiologija', 'Prizemlje', 1),
       (nextval('odeljenje_seq'), 'Neurologija', 'Prvi sprat', 1),
       (nextval('odeljenje_seq'), 'Hirurgija', 'Drugi sprat', 2),
       (nextval('odeljenje_seq'), 'Onkologija', 'Prizemlje', 3);

insert into dijagnoza(id, naziv, opis, oznaka)
values (nextval('dijagnoza_seq'), 'Hipertenzija', 'Povišen krvni pritisak', 'I10'),
       (nextval('dijagnoza_seq'), 'Dijabetes tip 2', 'Šećerna bolest tipa 2', 'E11'),
       (nextval('dijagnoza_seq'), 'Infarkt miokarda', 'Srčani udar', 'I21'),
       (nextval('dijagnoza_seq'), 'Moždani udar', 'Cerebrovaskularni incident', 'I64');

insert into pacijent(id, ime, prezime, zdr_osiguranje, datum_rodjenja, odeljenje_id, dijagnoza_id)
values (nextval('pacijent_seq'), 'Stefan', 'Nikolić', true, '1985-03-15', 1, 1),
       (nextval('pacijent_seq'), 'Milica', 'Savić', false, '1990-07-22', 2, 2),
       (nextval('pacijent_seq'), 'Nikola', 'Đorđević', true, '1978-11-30', 3, 3),
       (nextval('pacijent_seq'), 'Tamara', 'Stojanović', true, '2000-01-10', 4, 4);