import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../../../../core/services/producto.service';
import { CategoriaService, Categoria } from '../../../../core/services/categoria.service';
import { Producto } from '../../../../core/models/producto';

type VistaModal = 'crear' | 'editar' | 'eliminar';

@Component({
  selector: 'app-modal-ajustes-productos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './modal-ajustes-productos.component.html',
  styleUrls: ['./modal-ajustes-productos.component.css']
})
export class ModalAjustesProductosComponent implements OnInit {
  @Input() open: boolean = false;
  @Output() close = new EventEmitter<void>();

  vistaActual: VistaModal = 'crear';

  categorias: Categoria[] = [];
  productos: Producto[] = [];
  productosFiltrados: Producto[] = [];

  // Form fields
  nombreProducto: string = '';
  descripcion: string = '';
  precio: string = '';
  tipoSeleccionado: string = '';
  categoriaSeleccionadaId: number | null = null;
  productoSeleccionado: Producto | null = null;
  busquedaProducto: string = '';

  error: string = '';
  mensajeExito: string = '';

  tipos: string[] = ['bebida', 'comida'];

  constructor(
    private productoService: ProductoService,
    private categoriaService: CategoriaService
  ) {}

  ngOnInit(): void {
    this.cargarCategorias();
    this.cargarProductos();
  }

  cargarCategorias() {
    this.categoriaService.getCategorias().subscribe(c => this.categorias = c);
  }

  cargarProductos() {
    this.productoService.getAllProductos().subscribe(p => this.productos = p);
  }

  cerrar(): void {
    this.close.emit();
    this.resetearFormulario();
  }

  cambiarVista(vista: VistaModal) {
    this.vistaActual = vista;
    this.error = '';
    this.mensajeExito = '';
    this.resetearFormulario();
  }

  // ----------- CREAR -----------
  guardarProducto() {
    this.error = '';
    this.mensajeExito = '';

    if (!this.nombreProducto.trim() || !this.precio.trim() || !this.tipoSeleccionado || !this.categoriaSeleccionadaId) {
      this.error = 'Todos los campos son obligatorios.';
      return;
    }

    // Validar precio con coma
    const precioNum = parseFloat(this.precio.replace(',', '.'));
    if (isNaN(precioNum)) {
      this.error = 'El precio debe ser un número válido.';
      return;
    }

    // Validar nombre único por categoría
    const existe = this.productos.some(p =>
      p.nombre.trim().toLowerCase() === this.nombreProducto.trim().toLowerCase() &&
      p.tipo === this.tipoSeleccionado &&
      p['idCategoria'] === this.categoriaSeleccionadaId
    );
    if (existe) {
      this.error = 'Ya existe un producto con ese nombre y categoría.';
      return;
    }

    this.productoService.crearProducto({
      nombre: this.nombreProducto.trim(),
      precio: precioNum,
      tipo: this.tipoSeleccionado,
      idCategoria: this.categoriaSeleccionadaId,
      descripcion: this.descripcion
    }).subscribe({
      next: () => {
        this.mensajeExito = '¡Producto creado con éxito!';
        setTimeout(() => this.cerrar(), 1200);
      },
      error: () => {
        this.error = 'Error al crear el producto.';
      }
    });
  }

  // ----------- EDITAR -----------
  buscarProductos() {
    if (!this.busquedaProducto.trim()) {
      this.productosFiltrados = [];
      return;
    }
    this.productosFiltrados = this.productos
      .filter(p =>
        (!this.categoriaSeleccionadaId || p['idCategoria'] === this.categoriaSeleccionadaId) &&
        p.nombre.toLowerCase().includes(this.busquedaProducto.toLowerCase())
      )
      .slice(0, 20); // Limita resultados
  }

  seleccionarProductoEditar(producto: Producto) {
    console.log(producto);
    this.productoSeleccionado = producto;
    this.nombreProducto = producto.nombre;
    this.precio = producto.precio.toString().replace('.', ',');
    this.tipoSeleccionado = producto.tipo;
    this.categoriaSeleccionadaId = producto.idCategoria;
    this.descripcion = producto['descripcion'] || '';
    this.busquedaProducto = producto.nombre;
    this.productosFiltrados = [];
  }

  guardarEdicionProducto() {
    this.error = '';
    this.mensajeExito = '';

    if (!this.productoSeleccionado) {
      this.error = 'Selecciona un producto para editar.';
      return;
    }
    if (!this.nombreProducto.trim() || !this.precio.trim() || !this.tipoSeleccionado || !this.categoriaSeleccionadaId) {
      this.error = 'Todos los campos son obligatorios.';
      return;
    }
    const precioNum = parseFloat(this.precio.replace(',', '.'));
    if (isNaN(precioNum)) {
      this.error = 'El precio debe ser un número válido.';
      return;
    }
    // Validar nombre único por categoría (excepto el propio)
    const existe = this.productos.some(p =>
      p.id !== this.productoSeleccionado!.id &&
      p.nombre.trim().toLowerCase() === this.nombreProducto.trim().toLowerCase() &&
      p.tipo === this.tipoSeleccionado &&
      p['idCategoria'] === this.categoriaSeleccionadaId
    );
    if (existe) {
      this.error = 'Ya existe un producto con ese nombre y categoría.';
      return;
    }

    this.productoService.editarProducto(this.productoSeleccionado.id, {
      nombre: this.nombreProducto.trim(),
      precio: precioNum,
      tipo: this.tipoSeleccionado,
      idCategoria: this.categoriaSeleccionadaId,
      descripcion: this.descripcion
    }).subscribe({
      next: () => {
        this.mensajeExito = '¡Producto editado con éxito!';
        setTimeout(() => this.cerrar(), 1200);
      },
      error: () => {
        this.error = 'Error al editar el producto.';
      }
    });
  }

  // ----------- ELIMINAR -----------
  buscarProductosEliminar() {
    if (!this.busquedaProducto.trim()) {
      this.productosFiltrados = [];
      return;
    }
    this.productosFiltrados = this.productos
      .filter(p =>
        (!this.categoriaSeleccionadaId || p['idCategoria'] === this.categoriaSeleccionadaId) &&
        p.nombre.toLowerCase().includes(this.busquedaProducto.toLowerCase())
      )
      .slice(0, 20);
  }

  seleccionarProductoEliminar(producto: Producto) {
    this.productoSeleccionado = producto;
    this.busquedaProducto = producto.nombre;
    this.productosFiltrados = [];
  }

  eliminarProducto() {
    this.error = '';
    this.mensajeExito = '';
    if (!this.productoSeleccionado) {
      this.error = 'Selecciona un producto para eliminar.';
      return;
    }
    const confirmado = window.confirm(`¿Seguro que quieres eliminar el producto "${this.productoSeleccionado.nombre}"?`);
    if (!confirmado) return;

    this.productoService.eliminarProducto(this.productoSeleccionado.id).subscribe({
      next: () => {
        this.mensajeExito = '¡Producto eliminado con éxito!';
        setTimeout(() => this.cerrar(), 1200);
      },
      error: () => {
        this.error = 'Error al eliminar el producto.';
      }
    });
  }

  resetearFormulario() {
    this.nombreProducto = '';
    this.descripcion = '';
    this.precio = '';
    this.tipoSeleccionado = '';
    this.categoriaSeleccionadaId = null;
    this.productoSeleccionado = null;
    this.busquedaProducto = '';
    this.productosFiltrados = [];
    this.error = '';
    this.mensajeExito = '';
  }
}