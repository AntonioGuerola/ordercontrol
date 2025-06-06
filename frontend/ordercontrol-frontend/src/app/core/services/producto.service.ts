import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Producto } from '../models/producto';

@Injectable({
  providedIn: 'root',
})
export class ProductoService {
  private apiUrl = `${environment.apiUrl}/api/productos`;

  constructor(private http: HttpClient) {}

  getProductosPorCategoria(idCategoria: number): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/categoria/${idCategoria}`);
  }

  getAllProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl);
  }

  buscarProductosPorNombre(nombre: string): Observable<Producto[]> {
    return this.http.get<Producto[]>(
      `${this.apiUrl}?search=${encodeURIComponent(nombre)}`
    );
  }

  buscarProductos(
    nombre: string,
    idCategoria?: number
  ): Observable<Producto[]> {
    let url = `${this.apiUrl}?search=${encodeURIComponent(nombre)}`;
    if (idCategoria) url += `&categoria=${idCategoria}`;
    return this.http.get<Producto[]>(url);
  }

  crearProducto(producto: Partial<Producto>): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, producto);
  }

  editarProducto(
    id: number,
    producto: Partial<Producto>
  ): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${id}`, producto);
  }

  eliminarProducto(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' });
  }
}
