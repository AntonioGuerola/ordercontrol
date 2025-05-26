import { Routes } from '@angular/router';

import { MesaTipoSelectorComponent } from './pages/tpv/components/mesa-tipo-selector/mesa-tipo-selector.component';

export const routes: Routes = [
     { path: '', redirectTo: 'mesas', pathMatch: 'full' },

  {
    path: 'mesas',
    component: MesaTipoSelectorComponent
  },
];
