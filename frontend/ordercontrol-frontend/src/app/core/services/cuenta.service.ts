import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto } from '../models/producto';

@Injectable({
  providedIn: 'root',
})
export class CuentaService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  obtenerProductos(idMesa: number): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/cuentas/mesa/${idMesa}`);
  }

  actualizarProducto(idMesa: number, producto: Producto): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/cuentas/mesa/${idMesa}/producto/${producto.id}`, producto);
  }

  eliminarProducto(idMesa: number, idProducto: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/cuentas/mesa/${idMesa}/producto/${idProducto}`);
  }
}
