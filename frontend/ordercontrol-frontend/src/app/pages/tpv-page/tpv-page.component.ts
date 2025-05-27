import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MesaTipoSelectorComponent } from '../tpv/components/mesa-tipo-selector/mesa-tipo-selector.component';


@Component({
  selector: 'app-tpv-page',
  imports: [CommonModule, MesaTipoSelectorComponent],
  standalone: true,
  templateUrl: './tpv-page.component.html',
  styleUrl: './tpv-page.component.css'
})
export class TpvPageComponent {

}
