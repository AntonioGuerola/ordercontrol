import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CuentaService } from '../../../../core/services/cuenta.service';
import { Producto } from '../../../../core/models/producto';
import { Mesa } from '../../../../core/models/mesa';

@Component({
  selector: 'app-cuenta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cuenta.component.html',
  styleUrls: ['./cuenta.component.css'],
})
export class CuentaComponent implements OnInit {
  @Input() idMesa!: number;
  @Input() mesa!: Mesa | null;

  mesaNumero: number | null = null;
  productosConfirmados: Producto[] = [];
  productosPendientes: Producto[] = [];

  constructor(private cuentaService: CuentaService) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.cuentaService.obtenerProductos(this.idMesa).subscribe((productos) => {
      this.productosConfirmados = productos;
    });
  }

  agregarProductoPendiente(producto: Producto) {
    if (!this.mesa) {
      alert("Primero seleccione una mesa.");
      return;
    }

    const existente = this.productosPendientes.find(p => p.id === producto.id);

    if (existente) {
      existente.cantidad++;
    } else {
      this.productosPendientes.push({ ...producto, cantidad: 1 });
    }
  }

  incrementarCantidadPendiente(producto: Producto): void {
    producto.cantidad++;
  }

  decrementarCantidadPendiente(producto: Producto): void {
    if (producto.cantidad > 1) {
      producto.cantidad--;
    }
  }

  eliminarPendiente(producto: Producto): void {
    this.productosPendientes = this.productosPendientes.filter(p => p.id !== producto.id);
  }

  calcularSubtotal(): number {
    const subtotalConfirmados = this.productosConfirmados.reduce((sum, p) => sum + p.precio * p.cantidad, 0);
    const subtotalPendientes = this.productosPendientes.reduce((sum, p) => sum + p.precio * p.cantidad, 0);
    return subtotalConfirmados + subtotalPendientes;
  }

  calcularIVA(): number {
    return this.calcularSubtotal() * 0.21;
  }

  calcularTotal(): number {
    return this.calcularSubtotal() + this.calcularIVA();
  }

  establecerMesa(mesa: Mesa) {
    this.mesa = mesa;
    this.mesaNumero = mesa.numMesa;
    this.productosPendientes = [];
    this.cargarProductosConfirmados();
  }

  cargarProductosConfirmados() {
    if (!this.mesa) return;
    this.cuentaService.obtenerProductos(this.mesa.id).subscribe(productos => {
      this.productosConfirmados = productos;
    });
  }

  limpiarCuenta() {
    this.productosConfirmados = [];
    this.productosPendientes = [];
    this.mesa = null;
    this.mesaNumero = null;
  }
}
