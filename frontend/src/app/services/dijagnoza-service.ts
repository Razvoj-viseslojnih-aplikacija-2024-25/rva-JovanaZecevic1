import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dijagnoza } from '../models/dijagnoza';

@Injectable({
  providedIn: 'root'
})
export class DijagnozaService {
  constructor(private httpClient: HttpClient) {}

  public getAllDijagnoze(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/dijagnoze');
  }

  public createDijagnoza(dijagnoza: Dijagnoza): Observable<any> {
    return this.httpClient.post('http://localhost:8080/dijagnoza', dijagnoza);
  }

  public updateDijagnoza(dijagnoza: Dijagnoza): Observable<any> {
    return this.httpClient.put(`http://localhost:8080/dijagnoza/${dijagnoza.id}`, dijagnoza);
  }

  public deleteDijagnoza(id: number): Observable<any> {
    return this.httpClient.delete(`http://localhost:8080/dijagnoza/${id}`, {responseType: 'text'});
  }

  public getDijagnozasByNaziv(naziv: string): Observable<any> {
    return this.httpClient.get(`http://localhost:8080/dijagnoza/naziv/${naziv}`);
  }

  public getDijagnozasByOznaka(oznaka: string): Observable<any> {
    return this.httpClient.get(`http://localhost:8080/dijagnoza/oznaka/${oznaka}`);
  }
}