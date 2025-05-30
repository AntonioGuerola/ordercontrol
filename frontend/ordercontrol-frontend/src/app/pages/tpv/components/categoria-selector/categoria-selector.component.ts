import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CategoriaService, Categoria } from '../../../../core/services/categoria.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-categoria-selector',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './categoria-selector.component.html',
  styleUrls: ['./categoria-selector.component.css']
})
export class CategoriaSelectorComponent implements OnInit {
  categorias: Categoria[] = [];
  categoriaSeleccionadaId: number | null = null;

  @Output() categoriaSeleccionada = new EventEmitter<number>();

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.categoriaService.getCategorias().subscribe({
      next: (data) => {
        this.categorias = data;
        if (data.length > 0) {
          this.categoriaSeleccionadaId = data[0].id;
          this.categoriaSeleccionada.emit(data[0].id);
        }
      },
      error: (err) => console.error('Error al obtener categorías:', err)
    });
  }

  seleccionarCategoria(categoria: Categoria) {
    this.categoriaSeleccionadaId = categoria.id;
    this.categoriaSeleccionada.emit(categoria.id);
  }
}
