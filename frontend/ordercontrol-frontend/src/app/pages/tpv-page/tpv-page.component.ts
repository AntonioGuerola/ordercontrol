import { Component, ViewChild, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MesaTipoSelectorComponent } from '../tpv/components/mesa-tipo-selector/mesa-tipo-selector.component';
import { MesaGridComponent } from '../tpv/components/mesa-grid/mesa-grid.component';
import { CategoriaSelectorComponent } from '../tpv/components/categoria-selector/categoria-selector.component';
import { ProductoGridComponent } from '../tpv/components/producto-grid/producto-grid.component';
import { AccionesCuentaComponent } from '../tpv/components/acciones-cuenta/acciones-cuenta.component';
import { CuentaComponent } from '../tpv/components/cuenta/cuenta.component';
import { Producto } from '../../core/models/producto';
import { Mesa } from '../../core/models/mesa';
import { AjustesButtonsComponent } from "../tpv/components/ajustes-buttons/ajustes-buttons.component";
import { ModalAjustesCategoriasComponent } from "../tpv/components/modal-ajustes-categorias/modal-ajustes-categorias.component";

@Component({
  selector: 'app-tpv-page',
  imports: [
    CommonModule,
    MesaTipoSelectorComponent,
    MesaGridComponent,
    CategoriaSelectorComponent,
    ProductoGridComponent,
    AccionesCuentaComponent,
    CuentaComponent,
    AjustesButtonsComponent,
    ModalAjustesCategoriasComponent
],
  standalone: true,
  templateUrl: './tpv-page.component.html',
  styleUrl: './tpv-page.component.css',
})
export class TpvPageComponent {
  tipoMesaSeleccionada: string = 'Cafeteria';
  idCategoriaSeleccionada: number | null = null;

  @ViewChild(MesaGridComponent) mesaGridComponent!: MesaGridComponent;
  @ViewChild(CuentaComponent) cuentaComponent!: CuentaComponent;
@ViewChild('categoriaSelector') categoriaSelectorComponent!: CategoriaSelectorComponent;
  mesaSeleccionada: Mesa | null = null;
  modalCategoriasAbierto = false;

  constructor(private cdr: ChangeDetectorRef) {}

  seleccionarMesa(mesa: Mesa) {
    if (this.mesaSeleccionada?.id === mesa.id) {
      this.mesaSeleccionada = null;
      setTimeout(() => {
        this.mesaSeleccionada = mesa;
        this.cuentaComponent.establecerMesa(mesa);
      });
    } else {
      this.mesaSeleccionada = mesa;
      this.cuentaComponent.establecerMesa(mesa);
    }
  }

  onProductoSeleccionado(producto: Producto) {
    this.cuentaComponent.agregarProductoPendiente(producto);
  }

  actualizarTipoMesaSeleccionado(tipo: string) {
    this.tipoMesaSeleccionada = tipo;
  }

  actualizarCategoriaSeleccionada(idCategoria: number) {
    this.idCategoriaSeleccionada = idCategoria;
  }

  refrescarMesas() {
    if (this.mesaGridComponent) {
      this.mesaGridComponent.recargarMesas();
    }
  }

  onProductosEnviados() {
    this.cuentaComponent.productosPendientes = [];
    this.cuentaComponent.cargarProductosConfirmados();
    this.refrescarMesas();
    this.cdr.detectChanges();
  }

  onCuentaCobrada() {
    this.mesaSeleccionada = null;
    setTimeout(() => {
      this.refrescarMesas();
      this.cdr.detectChanges();
    }, 0);
  }

  mesaAnulada() {
    this.cuentaComponent.limpiarCuenta();
    this.mesaSeleccionada = null;
    setTimeout(() => {
      this.refrescarMesas();
      this.cdr.detectChanges();
    }, 0);
  }

  onCategoriasActualizadas() {
    if (this.categoriaSelectorComponent) {
      console.log('Recargando categorías')
      this.categoriaSelectorComponent.cargarCategorias();
    }
  }
}