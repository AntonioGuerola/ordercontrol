// mesa-tipo-selector.component.ts
import { Component, OnInit } from '@angular/core';
import { TipoMesaService } from '../../../../core/services/tipo-mesa.service';
import { TipoMesa } from '../../../../core/services/tipo-mesa.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-mesa-tipo-selector',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mesa-tipo-selector.component.html',
  styleUrls: ['./mesa-tipo-selector.component.css']
})
export class MesaTipoSelectorComponent implements OnInit {
  tiposMesa: TipoMesa[] = [];
  tipoSeleccionadoId: number | null = null;

  constructor(private tipoMesaService: TipoMesaService) {}

  ngOnInit(): void {
    this.tipoMesaService.getTiposMesa().subscribe((tipos) => {
      this.tiposMesa = tipos;
      if (tipos.length > 0) this.tipoSeleccionadoId = tipos[0].id;
    });
  }

  seleccionarTipo(id: number) {
    this.tipoSeleccionadoId = id;
    // Aquí puedes emitir un evento o llamar a un método para cargar las mesas
  }
}
