import { Component, Input, OnChanges, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductoService } from '../../../../core/services/producto.service';
import { Producto } from '../../../../core/models/producto';

@Component({
  selector: 'app-producto-grid',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './producto-grid.component.html',
  styleUrl: './producto-grid.component.css'
})
export class ProductoGridComponent implements OnChanges {
  @Input() idCategoriaSeleccionada: number | null = null;
  @Output() productoSeleccionado = new EventEmitter<Producto>();
  productos: Producto[] = [];

  constructor(private productoService: ProductoService) {}

  seleccionarProducto(producto: Producto) {
    this.productoSeleccionado.emit(producto);
  }

  ngOnChanges(): void {
    if (this.idCategoriaSeleccionada !== null) {
      this.productoService.getProductosPorCategoria(this.idCategoriaSeleccionada).subscribe({
        next: (data) => this.productos = data,
        error: (err) => console.error('Error al obtener productos:', err)
      });
    }
  }
}
