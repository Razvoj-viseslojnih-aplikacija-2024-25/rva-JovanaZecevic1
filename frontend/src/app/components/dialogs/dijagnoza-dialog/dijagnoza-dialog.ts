import { Component, Inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Dijagnoza } from '../../../models/dijagnoza';
import { DijagnozaService } from '../../../services/dijagnoza-service';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-dijagnoza-dialog',
  imports: [MatDialogModule, MatFormFieldModule, MatButtonModule, MatInputModule, FormsModule, CommonModule, MatIconModule],
  templateUrl: './dijagnoza-dialog.html',
  styleUrl: './dijagnoza-dialog.css'
})
export class DijagnozaDialog {
  flag!: number;

  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<DijagnozaDialog>,
    private dijagnozaService: DijagnozaService,
    @Inject(MAT_DIALOG_DATA) public data: Dijagnoza
  ) {}

  public add(): void {
    this.dijagnozaService.createDijagnoza(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Diagnosis ${data.naziv} successfully created!`, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while creating diagnosis', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public update(): void {
    this.dijagnozaService.updateDijagnoza(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Diagnosis ${data.naziv} successfully updated!`, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while updating diagnosis', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public delete(): void {
    this.dijagnozaService.deleteDijagnoza(this.data.id).subscribe({
      next: (response) => {
        this.dialogRef.close(1);
        this.snackBar.open(response, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while deleting diagnosis', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('No changes were made', 'OK', { duration: 2500 });
  }
}