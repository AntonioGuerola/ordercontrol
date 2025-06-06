import { Component } from '@angular/core';
import { ModalAjustesCategoriasComponent } from '../modal-ajustes-categorias/modal-ajustes-categorias.component';
import { ModalAjustesProductosComponent } from '../modal-ajustes-productos/modal-ajustes-productos.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ajustes-buttons',
  standalone: true,
  imports: [CommonModule, ModalAjustesCategoriasComponent, ModalAjustesProductosComponent],
  templateUrl: './ajustes-buttons.component.html',
})
export class AjustesButtonsComponent {
  modalCategoriasOpen = false;
  modalProductosOpen = false;

  cerrarModalCategorias() {
    this.modalCategoriasOpen = false;
  }

  cerrarModalProductos() {
    this.modalProductosOpen = false;
  }
}
