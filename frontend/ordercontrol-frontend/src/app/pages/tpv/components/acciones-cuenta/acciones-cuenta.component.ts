import { Component, Input, Output, EventEmitter } from '@angular/core';
import { AccionesCuentaService } from '../../../../core/services/acciones-cuenta.service';
import { Mesa } from '../../../../core/models/mesa';

@Component({
  selector: 'app-acciones-cuenta',
  templateUrl: './acciones-cuenta.component.html',
  styleUrls: ['./acciones-cuenta.component.css']
})
export class AccionesCuentaComponent {
  @Input() mesa!: Mesa;
  @Output() mesaAnulada = new EventEmitter<void>();

  constructor(private accionesCuentaService: AccionesCuentaService) {}

  cobrarCuenta() {
    this.accionesCuentaService.cobrarCuenta(this.mesa.id).subscribe(() => {
    });
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
}
