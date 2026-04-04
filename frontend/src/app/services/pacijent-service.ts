import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pacijent } from '../models/pacijent';

@Injectable({
  providedIn: 'root'
})
export class PacijentService {
  constructor(private httpClient: HttpClient) {}

  public getAllPacijenti(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/pacijenti');
  }

  public getPacijentiByOdeljenje(odeljenjeId: number): Observable<any> {
    return this.httpClient.get(`http://localhost:8080/pacijent/odeljenje/${odeljenjeId}`);
  }

  public getPacijentiByDijagnoza(dijagnozaId: number): Observable<any> {
    return this.httpClient.get(`http://localhost:8080/pacijent/dijagnoza/${dijagnozaId}`);
  }

  public createPacijent(pacijent: Pacijent): Observable<any> {
    return this.httpClient.post('http://localhost:8080/pacijent', pacijent);
  }

  public updatePacijent(pacijent: Pacijent): Observable<any> {
    return this.httpClient.put(`http://localhost:8080/pacijent/${pacijent.id}`, pacijent);
  }

  public deletePacijent(id: number): Observable<any> {
    return this.httpClient.delete(`http://localhost:8080/pacijent/${id}`, {responseType: 'text'});
  }
}