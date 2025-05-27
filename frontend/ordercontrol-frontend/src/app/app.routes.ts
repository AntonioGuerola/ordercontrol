import { Routes } from '@angular/router';
import { TpvPageComponent } from './pages/tpv-page/tpv-page.component';
import { HomeComponent } from './pages/home/home.component';
import { MesaTipoSelectorComponent } from './pages/tpv/components/mesa-tipo-selector/mesa-tipo-selector.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'tpv', component: TpvPageComponent },
];
