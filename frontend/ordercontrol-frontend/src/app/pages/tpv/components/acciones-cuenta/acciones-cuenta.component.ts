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

@Component({
  selector: 'app-acciones-cuenta',
  templateUrl: './acciones-cuenta.component.html',
  styleUrls: ['./acciones-cuenta.component.css'],
})
export class AccionesCuentaComponent {
  @Input() mesa!: Mesa;
  @Output() mesaAnulada = new EventEmitter<void>();

  @ViewChild(CuentaComponent) cuentaComponent!: CuentaComponent;

  constructor(private accionesCuentaService: AccionesCuentaService) {}

  cobrarCuenta() {
    this.accionesCuentaService.cobrarCuenta(this.mesa.id).subscribe(() => {});
  }

  imprimirCuenta() {
    this.accionesCuentaService.imprimirCuenta(this.mesa.id);
  }

  anularCuenta() {
    if (confirm('¿Está seguro de que desea anular la cuenta de la mesa?')) {
      this.accionesCuentaService.anularCuenta(this.mesa.id).subscribe(() => {
        this.mesaAnulada.emit();
      });
    }
  }

  enviarComanda() {
    const productos = this.cuentaComponent.productosPendientes;
    const mesa = this.cuentaComponent.mesa;

    if (!mesa) {
      alert('No hay mesa seleccionada.');
      return;
    }

    if (!productos.length) {
      alert('No hay productos para enviar.');
      return;
    }

    this.accionesCuentaService
      .obtenerOCrearComanda(mesa.id)
      .subscribe((comanda) => {
        const peticiones = productos.map((producto) =>
          this.accionesCuentaService.agregarProductoAComanda(
            comanda.id,
            producto
          )
        );

        forkJoin(peticiones).subscribe(() => {
          const cocina = productos
            .filter((p) => p.tipo === 'COCINA')
            .map((p) => p.nombre);
          const barra = productos
            .filter((p) => p.tipo === 'BARRA')
            .map((p) => p.nombre);

          if (cocina.length) alert(`Enviado a cocina: ${cocina.join(', ')}`);
          if (barra.length) alert(`Enviado a barra: ${barra.join(', ')}`);

          this.cuentaComponent.productosPendientes = [];
          this.cuentaComponent.cargarProductosConfirmados();
        });
      });
  }
}
