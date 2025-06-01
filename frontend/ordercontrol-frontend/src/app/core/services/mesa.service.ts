import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Mesa } from '../models/mesa';

export type {Mesa}; 

@Injectable({
  providedIn: 'root'
})
export class MesaService {
  private apiUrl = `${environment.apiUrl}/api/mesas`;

  constructor(private http: HttpClient) {}

  getMesasPorTipo(tipo: string): Observable<Mesa[]> {
    return this.http.get<Mesa[]>(`${this.apiUrl}/tipo/${tipo}`);
  }
}
