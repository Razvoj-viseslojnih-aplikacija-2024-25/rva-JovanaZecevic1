import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Bolnica } from '../../../models/bolnica';
import { BolnicaService } from '../../../services/bolnica-service';
import { MatDialog } from '@angular/material/dialog';
import { BolnicaDialog } from '../../dialogs/bolnica-dialog/bolnica-dialog';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-bolnica-component',
  imports: [MatTableModule, MatIconModule, MatToolbarModule, MatSortModule, MatPaginatorModule],
  templateUrl: './bolnica-component.html',
  styleUrl: './bolnica-component.css',
})
export class BolnicaComponent implements OnInit {
  displayedColumns = ['id', 'naziv', 'adresa', 'budzet', 'actions'];
  dataSource!: MatTableDataSource<Bolnica>;

  constructor(private bolnicaService: BolnicaService, private dialog: MatDialog) {}

  @ViewChild(MatSort, { static: false }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(): void {
    this.bolnicaService.getAllBolnice().subscribe({
      next: (data) => {
        console.log(data);
        this.dataSource = new MatTableDataSource<Bolnica>(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: (error) => {
        console.log(error.message);
      },
    });
  }

  public openDialog(flag: number, id?: number, naziv?: String, adresa?: String, budzet?: number): void {
    const ref = this.dialog.open(BolnicaDialog, { data: { id, naziv, adresa, budzet } });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response === 1) {
        this.ngOnInit();
      }
    });
  }
}