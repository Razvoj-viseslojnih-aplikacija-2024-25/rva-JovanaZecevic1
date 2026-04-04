import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Odeljenje } from '../../../models/odeljenje';
import { OdeljenjeService } from '../../../services/odeljenje-service';
import { BolnicaService } from '../../../services/bolnica-service';
import { Bolnica } from '../../../models/bolnica';

@Component({
  selector: 'app-odeljenje-dialog',
  imports: [MatDialogModule, MatFormFieldModule, MatButtonModule, MatInputModule, MatSelectModule, FormsModule, CommonModule],
  templateUrl: './odeljenje-dialog.html',
  styleUrl: './odeljenje-dialog.css'
})
export class OdeljenjeDialog implements OnInit {
  flag!: number;
  bolnice!: Bolnica[];

  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<OdeljenjeDialog>,
    private odeljenjeService: OdeljenjeService,
    private bolnicaService: BolnicaService,
    @Inject(MAT_DIALOG_DATA) public data: Odeljenje
  ) {}

  ngOnInit(): void {
    this.bolnicaService.getAllBolnice().subscribe((data) => {
      this.bolnice = data;
    });
  }

  public compare(a: any, b: any) {
    return a && b && a.id == b.id;
  }

  public add(): void {
    this.odeljenjeService.createOdeljenje(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Department ${data.naziv} successfully created!`, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while creating department', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public update(): void {
    this.odeljenjeService.updateOdeljenje(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Department ${data.naziv} successfully updated!`, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while updating department', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public delete(): void {
    this.odeljenjeService.deleteOdeljenje(this.data.id).subscribe({
      next: (response) => {
        this.dialogRef.close(1);
        this.snackBar.open(response, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while deleting department', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('No changes were made', 'OK', { duration: 2500 });
  }
}