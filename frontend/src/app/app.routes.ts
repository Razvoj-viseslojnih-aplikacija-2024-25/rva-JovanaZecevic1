import { Routes } from '@angular/router';
import { BolnicaComponent } from './components/main/bolnica-component/bolnica-component';
import { OdeljenjeComponent } from './components/main/odeljenje-component/odeljenje-component';
import { DijagnozaComponent } from './components/main/dijagnoza-component/dijagnoza-component';
import { PacijentComponent } from './components/main/pacijent-component/pacijent-component';
import { HomeComponent } from './components/utility/home-component/home-component';
import { AboutComponent } from './components/utility/about-component/about-component';
import { AuthorComponent } from './components/utility/author-component/author-component';

export const routes: Routes = [
  { path: 'bolnica', component: BolnicaComponent },
  { path: 'odeljenje', component: OdeljenjeComponent },
  { path: 'dijagnoza', component: DijagnozaComponent },
  { path: 'pacijent', component: PacijentComponent },
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'author', component: AuthorComponent },
  { path: '', component: HomeComponent, pathMatch: 'full' }
];