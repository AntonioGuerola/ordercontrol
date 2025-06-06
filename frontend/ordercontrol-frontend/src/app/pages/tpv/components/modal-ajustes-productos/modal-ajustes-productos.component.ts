import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modal-ajustes-productos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './modal-ajustes-productos.component.html',
  styleUrls: ['./modal-ajustes-productos.component.css']
})
export class ModalAjustesProductosComponent {
  @Input() open = false;
  @Output() close = new EventEmitter<void>();

  cerrar() {
    this.close.emit();
  }
}
