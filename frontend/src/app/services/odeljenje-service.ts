import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Odeljenje } from '../models/odeljenje';

@Injectable({
  providedIn: 'root'
})
export class OdeljenjeService {
  constructor(private httpClient: HttpClient) {}

  public getAllOdeljenja(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/odeljenja');
  }

  public getOdeljenjaByBolnica(bolnicaId: number): Observable<any> {
    return this.httpClient.get(`http://localhost:8080/odeljenje/bolnica/${bolnicaId}`);
  }

  public createOdeljenje(odeljenje: Odeljenje): Observable<any> {
    return this.httpClient.post('http://localhost:8080/odeljenje', odeljenje);
  }

  public updateOdeljenje(odeljenje: Odeljenje): Observable<any> {
    return this.httpClient.put(`http://localhost:8080/odeljenje/${odeljenje.id}`, odeljenje);
  }

  public deleteOdeljenje(id: number): Observable<any> {
    return this.httpClient.delete(`http://localhost:8080/odeljenje/${id}`, {responseType: 'text'});
  }
}