import {
  Component,
  Input,
  Output,
  EventEmitter,
  ViewChild,
} from '@angular/core';
import { AccionesCuentaService } from '../../../../core/services/acciones-cuenta.service';
import { Mesa } from '../../../../core/models/mesa';
import { CuentaComponent } from '../cuenta/cuenta.component';
import { forkJoin } from 'rxjs';
import { Producto } from '../../../../core/models/producto';

@Component({
  selector: 'app-acciones-cuenta',
  templateUrl: './acciones-cuenta.component.html',
  styleUrls: ['./acciones-cuenta.component.css'],
})
export class AccionesCuentaComponent {
  @Output() mesaAnulada = new EventEmitter<void>();

  @Input() productosPendientes: Producto[] = [];
  @Input() mesa: Mesa | null = null;
  @Output() productosEnviados = new EventEmitter<void>();
  @Output() cuentaCobrada = new EventEmitter<void>();
  @Output() refrescarEstadoMesa = new EventEmitter<void>();

  constructor(private accionesCuentaService: AccionesCuentaService) {}

  cobrarCuenta() {
    if (!this.mesa) {
      alert('No hay mesa seleccionada.');
      return;
    }

    this.accionesCuentaService.cobrarCuenta(this.mesa.id).subscribe(() => {
      alert('Cuenta cobrada correctamente.');
      this.cuentaCobrada.emit();
    });
  }

  imprimirCuenta() {
    if (!this.mesa) {
      alert('No hay mesa seleccionada.');
      return;
    }
    this.accionesCuentaService.imprimirCuenta(this.mesa.id);
  }

  anularCuenta() {
    if (!this.mesa) {
      alert('No hay mesa seleccionada.');
      return;
    }

    if (confirm('¿Está seguro de que desea anular la cuenta de la mesa?')) {
      this.accionesCuentaService.anularCuenta(this.mesa.id).subscribe({
        next: () => {
          this.mesaAnulada.emit();
          this.refrescarEstadoMesa.emit();
        },
        error: (err) => {
          console.error('Error al anular mesa:', err);
          alert('Error al anular mesa.');
        },
      });
    }
  }

  enviarComanda() {
    if (!this.mesa) {
      alert('No hay mesa seleccionada.');
      return;
    }

    if (!this.productosPendientes.length) {
      alert('No hay productos para enviar.');
      return;
    }

    this.accionesCuentaService
      .obtenerOCrearComanda(this.mesa.id)
      .subscribe((comanda) => {
        const peticiones = this.productosPendientes.map((producto) =>
          this.accionesCuentaService.agregarProductoAComanda(
            comanda.id,
            producto
          )
        );

        forkJoin(peticiones).subscribe(() => {
          const cocina = this.productosPendientes
            .filter((p) => p.tipo === 'COCINA')
            .map((p) => p.nombre);
          const barra = this.productosPendientes
            .filter((p) => p.tipo === 'BARRA')
            .map((p) => p.nombre);

          if (cocina.length) alert(`Enviado a cocina: ${cocina.join(', ')}`);
          if (barra.length) alert(`Enviado a barra: ${barra.join(', ')}`);

          this.productosEnviados.emit();
          this.refrescarEstadoMesa.emit();
        });
      });
  }
}
