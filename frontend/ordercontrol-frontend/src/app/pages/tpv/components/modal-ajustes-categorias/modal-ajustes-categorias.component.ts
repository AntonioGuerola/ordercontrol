import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CategoriaService, Categoria } from '../../../../core/services/categoria.service';

type VistaModal = 'crear' | 'editar' | 'eliminar';

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
  @Output() categoriasActualizadas = new EventEmitter<void>();

  categorias: Categoria[] = [];
  nombreCategoria: string = '';
  categoriaSeleccionada: Categoria | null = null;
  vistaActual: VistaModal = 'crear';
  error: string = '';
  mensajeExito: string = '';

  constructor(
    private categoriaService: CategoriaService,
    private categoriaUpdateService: CategoriaService
  ) {}

  ngOnInit(): void {
    this.cargarCategorias();
  }

  cerrar(): void {
    this.open = false;
    this.close.emit();
    this.resetearFormulario();
  }

  cambiarVista(vista: VistaModal) {
    this.vistaActual = vista;
    this.error = '';
    this.mensajeExito = '';
    this.nombreCategoria = '';
    this.categoriaSeleccionada = null;
  }

  cargarCategorias(): void {
    this.categoriaService.getCategorias().subscribe(c => this.categorias = c);
  }

  formatearNombre(nombre: string): string {
    return nombre
      .toLowerCase()
      .replace(/\b\w/g, l => l.toUpperCase())
      .replace(/\s+/g, ' ')
      .trim();
  }

  crearCategoria(): void {
    this.error = '';
    this.mensajeExito = '';
    if (!this.nombreCategoria.trim()) {
      this.error = 'El nombre de la categoría no puede estar vacío.';
      return;
    }
    const nombreFormateado = this.formatearNombre(this.nombreCategoria);
    this.categoriaService.crearCategoria({ nombre: nombreFormateado }).subscribe({
      next: () => {
        this.mensajeExito = '¡Categoría creada con éxito!';
        this.cargarCategorias();
        console.log('Emitimos evento categoriasActualizadas');
        this.categoriasActualizadas.emit();
        setTimeout(() => this.cerrar(), 1200); // Espera para mostrar el mensaje
      },
      error: (err) => {
        if (err?.error?.message?.includes('Ya existe una categoría')) {
          this.error = 'Ya existe una categoría con ese nombre.';
        } else if (err?.error?.message?.includes('vacío')) {
          this.error = 'El nombre de la categoría no puede estar vacío.';
        } else {
          this.error = 'Error al crear la categoría.';
        }
      }
    });
  }

  editarCategoria(): void {
    this.error = '';
    this.mensajeExito = '';
    if (!this.categoriaSeleccionada) {
      this.error = 'Selecciona una categoría.';
      return;
    }
    if (!this.nombreCategoria.trim()) {
      this.error = 'El nombre de la categoría no puede estar vacío.';
      return;
    }
    const nombreFormateado = this.formatearNombre(this.nombreCategoria);
    this.categoriaService.actualizarCategoria(this.categoriaSeleccionada.id, { nombre: nombreFormateado }).subscribe({
      next: () => {
        this.mensajeExito = '¡Categoría editada con éxito!';
        this.cargarCategorias();
        console.log('Emitimos evento categoriasActualizadas');
        this.categoriasActualizadas.emit();
        setTimeout(() => this.cerrar(), 1200);
      },
      error: (err) => {
        if (err?.error?.message?.includes('Ya existe una categoría')) {
          this.error = 'Ya existe una categoría con ese nombre.';
        } else if (err?.error?.message?.includes('vacío')) {
          this.error = 'El nombre de la categoría no puede estar vacío.';
        } else {
          this.error = 'Error al editar la categoría.';
        }
      }
    });
  }

eliminarCategoria(): void {
  this.error = '';
  this.mensajeExito = '';
  if (!this.categoriaSeleccionada) {
    this.error = 'Selecciona una categoría.';
    return;
  }
  const confirmado = window.confirm(`¿Seguro que quieres eliminar la categoría "${this.categoriaSeleccionada.nombre}"?`);
  if (!confirmado) return;

  this.categoriaService.eliminarCategoria(this.categoriaSeleccionada.id).subscribe({
    next: () => {
      this.mensajeExito = '¡Categoría eliminada con éxito!';
      this.categoriasActualizadas.emit();
      setTimeout(() => {
        this.cargarCategorias();
        this.cerrar();
      }, 1200);
    },
    error: (err) => {
      this.error = 'Error al eliminar la categoría.';
    }
  });
}

  seleccionarCategoria(categoria: Categoria): void {
    this.categoriaSeleccionada = categoria;
    this.nombreCategoria = categoria.nombre;
    this.error = '';
    this.mensajeExito = '';
  }

  onCategoriaSeleccionada() {
    if (this.categoriaSeleccionada) {
      this.nombreCategoria = this.categoriaSeleccionada.nombre;
    } else {
      this.nombreCategoria = '';
    }
    this.error = '';
    this.mensajeExito = '';
  }

  resetearFormulario() {
    this.nombreCategoria = '';
    this.categoriaSeleccionada = null;
    this.vistaActual = 'crear';
    this.error = '';
    this.mensajeExito = 'Realizado con éxito';
  }
}