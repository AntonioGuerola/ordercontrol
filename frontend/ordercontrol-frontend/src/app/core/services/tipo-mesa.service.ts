import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface TipoMesa {
  id: number;
  nombre: string;
}

@Injectable({
  providedIn: 'root'
})
export class TipoMesaService {
  private apiUrl = `${environment.apiUrl}/api/tipos-mesa`;

  constructor(private http: HttpClient) {}

  getTiposMesa(): Observable<TipoMesa[]> {
    return this.http.get<TipoMesa[]>(this.apiUrl);
  }
}
