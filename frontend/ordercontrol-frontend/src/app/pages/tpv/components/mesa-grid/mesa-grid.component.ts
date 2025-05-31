import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Mesa, MesaService } from '../../../../core/services/mesa.service';

@Component({
  selector: 'app-mesa-grid',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mesa-grid.component.html',
  styleUrl: './mesa-grid.component.css'
})
export class MesaGridComponent implements OnChanges {
  @Input() tipoMesaSeleccionado: string | null = null;

  mesas: Mesa[] = [];
  mesaSeleccionada: number | null = null;

  constructor(private mesaService: MesaService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['tipoMesaSeleccionado'] && this.tipoMesaSeleccionado) {
      this.mesaService.getMesasPorTipo(this.tipoMesaSeleccionado)
        .subscribe({
          next: (mesas) => this.mesas = mesas,
          error: (err) => console.error('Error al obtener mesas:', err)
        });
    }
  }

  getEstadoColor(estado: string): string {
    switch (estado.toLowerCase()) {
      case 'libre':
        return 'bg-green-100 border-green-300 text-green-800 hover:bg-green-200';
      case 'ocupada':
        return 'bg-red-100 border-red-300 text-red-800 hover:bg-red-200';
      default:
        return 'bg-gray-100 border-gray-300 text-gray-800 hover:bg-gray-200';
    }
  }

  seleccionarMesa(numero: number) {
    this.mesaSeleccionada = numero;
  }

  recargarMesas() {
  if (this.tipoMesaSeleccionado) {
    this.mesaService.getMesasPorTipo(this.tipoMesaSeleccionado).subscribe({
      next: (mesas) => this.mesas = mesas,
      error: (err) => console.error('Error al recargar mesas:', err)
    });
  }
}
}
