import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Producto {
  id: number;
  nombre: string;
  descripcion: string;
  tipo: string;
  idCategoria: number;
  nombreCategoria: string;
  disponible: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = `${environment.apiUrl}/api/productos`;

  constructor(private http: HttpClient) {}

  getProductosPorCategoria(idCategoria: number): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/categoria/${idCategoria}`);
  }
}
