import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CategoriaService, Categoria } from '../../../../core/services/categoria.service';

@Component({
  selector: 'app-modal-ajustes-categorias',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './modal-ajustes-categorias.component.html',
  styleUrls: ['./modal-ajustes-categorias.component.css']
})
export class ModalAjustesCategoriasComponent implements OnInit {
  @Input() open: boolean = false;
  @Output() close = new EventEmitter<void>();

  categorias: Categoria[] = [];
  nombreCategoria: string = '';
  categoriaSeleccionada: Categoria | null = null;

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.cargarCategorias();
  }

  cerrar(): void {
    this.close.emit();
    this.nombreCategoria = '';
    this.categoriaSeleccionada = null;
  }

  cargarCategorias(): void {
    this.categoriaService.getCategorias().subscribe(c => this.categorias = c);
  }

  crearCategoria(): void {
    if (this.nombreCategoria.trim()) {
      this.categoriaService.crearCategoria({ nombre: this.nombreCategoria }).subscribe(() => {
        this.cargarCategorias();
        this.nombreCategoria = '';
      });
    }
  }

  editarCategoria(): void {
    if (this.categoriaSeleccionada && this.nombreCategoria.trim()) {
      this.categoriaService.actualizarCategoria(this.categoriaSeleccionada.id, { nombre: this.nombreCategoria }).subscribe(() => {
        this.cargarCategorias();
        this.nombreCategoria = '';
        this.categoriaSeleccionada = null;
      });
    }
  }

  eliminarCategoria(): void {
    if (this.categoriaSeleccionada) {
      this.categoriaService.eliminarCategoria(this.categoriaSeleccionada.id).subscribe(() => {
        this.cargarCategorias();
        this.categoriaSeleccionada = null;
      });
    }
  }

  seleccionarCategoria(categoria: Categoria): void {
    this.categoriaSeleccionada = categoria;
    this.nombreCategoria = categoria.nombre;
  }
}
