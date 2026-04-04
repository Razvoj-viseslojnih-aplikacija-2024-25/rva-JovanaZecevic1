import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Odeljenje } from '../../../models/odeljenje';
import { OdeljenjeService } from '../../../services/odeljenje-service';
import { MatDialog } from '@angular/material/dialog';
import { OdeljenjeDialog } from '../../dialogs/odeljenje-dialog/odeljenje-dialog';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-odeljenje-component',
  imports: [MatTableModule, MatIconModule, MatToolbarModule, MatSortModule, MatPaginatorModule],
  templateUrl: './odeljenje-component.html',
  styleUrl: './odeljenje-component.css',
})
export class OdeljenjeComponent implements OnInit {
  displayedColumns = ['id', 'naziv', 'lokacija', 'bolnica', 'actions'];
  dataSource!: MatTableDataSource<Odeljenje>;

  constructor(private odeljenjeService: OdeljenjeService, private dialog: MatDialog) {}

  @ViewChild(MatSort, { static: false }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(): void {
    this.odeljenjeService.getAllOdeljenja().subscribe({
      next: (data) => {
        this.dataSource = new MatTableDataSource<Odeljenje>(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: (error) => {
        console.log(error.message);
      },
    });
  }

  public openDialog(flag: number, id?: number, naziv?: String, lokacija?: String, bolnica?: any): void {
    const ref = this.dialog.open(OdeljenjeDialog, { data: { id, naziv, lokacija, bolnica } });
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe((response) => {
      if (response === 1) {
        this.ngOnInit();
      }
    });
  }
}