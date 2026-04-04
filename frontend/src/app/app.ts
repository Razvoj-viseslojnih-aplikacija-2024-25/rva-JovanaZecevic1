import { Component, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatNavList } from '@angular/material/list';
import { MatSidenav, MatSidenavContainer, MatSidenavContent } from '@angular/material/sidenav';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatButton, MatSidenav, MatSidenavContainer, MatSidenavContent, MatExpansionModule, MatIconModule, MatNavList, RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('Bolnica');
}