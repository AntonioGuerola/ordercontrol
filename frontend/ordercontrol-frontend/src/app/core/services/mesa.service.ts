import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Mesa {
  id: number;
  numMesa: number;
  estado: string;
  fechaHora?: string;
  tipo?: string;
}

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
