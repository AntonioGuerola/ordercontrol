import { Component, Input, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';

interface Mesa {
  id: number;
  numMesa: number;
  estado: string;
  fechaHora?: string;
  tipo?: string;
}

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

  ngOnChanges(): void {
    if (this.tipoMesaSeleccionado) {
      fetch(`http://localhost:8080/api/mesas/tipo/${this.tipoMesaSeleccionado}`)
        .then(res => res.json())
        .then(data => {
          this.mesas = data;
        })
        .catch(err => console.error('Error al obtener mesas:', err));
    }
  }

  getEstadoColor(estado: string): string {
    switch (estado.toLowerCase()) {
      case 'libre':
        return 'bg-green-100 border-green-300 text-green-800 hover:bg-green-200';
      case 'ocupada':
        return 'bg-red-100 border-red-300 text-red-800 hover:bg-red-200';
      case 'reservada':
        return 'bg-yellow-100 border-yellow-300 text-yellow-800 hover:bg-yellow-200';
      default:
        return 'bg-gray-100 border-gray-300 text-gray-800 hover:bg-gray-200';
    }
  }

  seleccionarMesa(numero: number) {
    this.mesaSeleccionada = numero;
  }
}
