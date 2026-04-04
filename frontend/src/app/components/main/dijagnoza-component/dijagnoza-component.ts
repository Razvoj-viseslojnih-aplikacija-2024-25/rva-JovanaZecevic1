import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Dijagnoza } from '../../../models/dijagnoza';
import { DijagnozaService } from '../../../services/dijagnoza-service';
import { MatDialog } from '@angular/material/dialog';
import { DijagnozaDialog } from '../../dialogs/dijagnoza-dialog/dijagnoza-dialog';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-dijagnoza-component',
  imports: [MatTableModule, MatIconModule, MatToolbarModule, MatSortModule, MatPaginatorModule],
  templateUrl: './dijagnoza-component.html',
  styleUrl: './dijagnoza-component.css',
})
export class DijagnozaComponent implements OnInit {
  displayedColumns = ['id', 'naziv', 'opis', 'oznaka', 'actions'];
  dataSource!: MatTableDataSource<Dijagnoza>;

  constructor(private dijagnozaService: DijagnozaService, private dialog: MatDialog) {}

  @ViewChild(MatSort, { static: false }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(): void {
    this.dijagnozaService.getAllDijagnoze().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource<Dijagnoza>(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: (error) => {
        console.log(error.message);
      },
    });
  }

  public openDialog(flag: number, id?: number, naziv?: String, opis?: String, oznaka?: String): void {
    const ref = this.dialog.open(DijagnozaDialog, { data: { id, naziv, opis, oznaka } });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response === 1) {
        this.ngOnInit();
      }
    });
  }
}