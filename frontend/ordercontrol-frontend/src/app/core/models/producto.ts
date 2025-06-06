export interface Producto {
  id: number;
  nombre: string;
  precio: number;
  tipo: string;
  cantidad: number;
  idCategoria: number;
  descripcion?: string;
}
