import { Component, Input, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductoService, Producto } from '../../../../core/services/producto.service';

@Component({
  selector: 'app-producto-grid',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './producto-grid.component.html',
  styleUrl: './producto-grid.component.css'
})
export class ProductoGridComponent implements OnChanges {
  @Input() idCategoriaSeleccionada: number | null = null;
  productos: Producto[] = [];

  constructor(private productoService: ProductoService) {}

  ngOnChanges(): void {
    if (this.idCategoriaSeleccionada !== null) {
      this.productoService.getProductosPorCategoria(this.idCategoriaSeleccionada).subscribe({
        next: (data) => this.productos = data,
        error: (err) => console.error('Error al obtener productos:', err)
      });
    }
  }
}
