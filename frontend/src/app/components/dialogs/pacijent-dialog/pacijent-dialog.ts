import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Pacijent } from '../../../models/pacijent';
import { PacijentService } from '../../../services/pacijent-service';
import { OdeljenjeService } from '../../../services/odeljenje-service';
import { DijagnozaService } from '../../../services/dijagnoza-service';
import { Odeljenje } from '../../../models/odeljenje';
import { Dijagnoza } from '../../../models/dijagnoza';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-pacijent-dialog',
  imports: [MatDialogModule, MatFormFieldModule, MatButtonModule, MatInputModule, MatSelectModule, MatCheckboxModule, FormsModule, CommonModule, MatIconModule],
  templateUrl: './pacijent-dialog.html',
  styleUrl: './pacijent-dialog.css'
})
export class PacijentDialog implements OnInit {
  flag!: number;
  odeljenja!: Odeljenje[];
  dijagnoze!: Dijagnoza[];

  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<PacijentDialog>,
    private pacijentService: PacijentService,
    private odeljenjeService: OdeljenjeService,
    private dijagnozaService: DijagnozaService,
    @Inject(MAT_DIALOG_DATA) public data: Pacijent
  ) {}

  ngOnInit(): void {
    this.odeljenjeService.getAllOdeljenja().subscribe((data) => {
      this.odeljenja = data;
    });
    this.dijagnozaService.getAllDijagnoze().subscribe((data) => {
      this.dijagnoze = data;
    });
  }

  public compare(a: any, b: any) {
    return a && b && a.id == b.id;
  }

  public add(): void {
    this.pacijentService.createPacijent(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Patient ${data.ime} ${data.prezime} successfully created!`, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while creating patient', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public update(): void {
    this.pacijentService.updatePacijent(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Patient ${data.ime} ${data.prezime} successfully updated!`, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while updating patient', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public delete(): void {
    this.pacijentService.deletePacijent(this.data.id).subscribe({
      next: (response) => {
        this.dialogRef.close(1);
        this.snackBar.open(response, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while deleting patient', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('No changes were made', 'OK', { duration: 2500 });
  }
}