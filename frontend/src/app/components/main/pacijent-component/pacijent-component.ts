import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Pacijent } from '../../../models/pacijent';
import { PacijentService } from '../../../services/pacijent-service';
import { MatDialog } from '@angular/material/dialog';
import { PacijentDialog } from '../../dialogs/pacijent-dialog/pacijent-dialog';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-pacijent-component',
  imports: [MatTableModule, MatIconModule, MatToolbarModule, MatSortModule, MatPaginatorModule],
  templateUrl: './pacijent-component.html',
  styleUrl: './pacijent-component.css',
})
export class PacijentComponent implements OnInit {
  displayedColumns = ['id', 'ime', 'prezime', 'zdr_osiguranje', 'datum_rodjenja', 'odeljenje', 'dijagnoza', 'actions'];
  dataSource!: MatTableDataSource<Pacijent>;

  constructor(private pacijentService: PacijentService, private dialog: MatDialog) {}

  @ViewChild(MatSort, { static: false }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(): void {
    this.pacijentService.getAllPacijenti().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource<Pacijent>(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: (error) => {
        console.log(error.message);
      },
    });
  }

  public openDialog(flag: number, id?: number, ime?: String, prezime?: String, zdr_osiguranje?: boolean, datum_rodjenja?: String, odeljenje?: any, dijagnoza?: any): void {
    const ref = this.dialog.open(PacijentDialog, { data: { id, ime, prezime, zdr_osiguranje, datum_rodjenja, odeljenje, dijagnoza } });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response === 1) {
        this.ngOnInit();
      }
    });
  }
}