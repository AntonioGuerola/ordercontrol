import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MesaTipoSelectorComponent } from '../tpv/components/mesa-tipo-selector/mesa-tipo-selector.component';
import { MesaGridComponent } from '../tpv/components/mesa-grid/mesa-grid.component';
import { CategoriaSelectorComponent } from '../tpv/components/categoria-selector/categoria-selector.component';
import { ProductoGridComponent } from '../tpv/components/producto-grid/producto-grid.component';
import { AccionesCuentaComponent } from '../tpv/components/acciones-cuenta/acciones-cuenta.component';
import { CuentaComponent } from '../tpv/components/cuenta/cuenta.component';


@Component({
  selector: 'app-tpv-page',
  imports: [CommonModule, MesaTipoSelectorComponent, MesaGridComponent, CategoriaSelectorComponent, ProductoGridComponent, AccionesCuentaComponent, CuentaComponent],
  standalone: true,
  templateUrl: './tpv-page.component.html',
  styleUrl: './tpv-page.component.css'
})
export class TpvPageComponent {
  tipoMesaSeleccionada: string = 'Cafeteria';
  idCategoriaSeleccionada: number | null = null;

  actualizarTipoMesaSeleccionado(tipo: string) {
    this.tipoMesaSeleccionada = tipo;
  }

  actualizarCategoriaSeleccionada(idCategoria: number) {
    this.idCategoriaSeleccionada = idCategoria;
  }
}

