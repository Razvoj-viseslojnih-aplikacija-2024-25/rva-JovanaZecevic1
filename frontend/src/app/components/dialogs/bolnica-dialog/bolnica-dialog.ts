import { Component, Inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Bolnica } from '../../../models/bolnica';
import { BolnicaService } from '../../../services/bolnica-service';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-bolnica-dialog',
  imports: [MatDialogModule, MatFormFieldModule, MatButtonModule, MatInputModule, FormsModule, CommonModule, MatIconModule],
  templateUrl: './bolnica-dialog.html',
  styleUrl: './bolnica-dialog.css'
})
export class BolnicaDialog {
  flag!: number;
  constructor(
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<BolnicaDialog>,
    private bolnicaService: BolnicaService,
    @Inject(MAT_DIALOG_DATA) public data: Bolnica
  ) {}

  public add(): void {
    this.bolnicaService.createBolnica(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Hospital ${data.naziv} successfully created!`, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while creating hospital', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public update(): void {
    this.bolnicaService.updateBolnica(this.data).subscribe({
      next: (data) => {
        this.dialogRef.close(1);
        this.snackBar.open(`Hospital ${data.naziv} successfully updated!`, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while updating hospital', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public delete(): void {
    this.bolnicaService.deleteBolnica(this.data.id).subscribe({
      next: (response) => {
        this.dialogRef.close(1);
        this.snackBar.open(response, 'OK', { duration: 2500 });
      },
      error: (error) => {
        this.snackBar.open('Error while deleting hospital', 'OK', { duration: 2500 });
        console.log(error.message);
      }
    });
  }

  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('No changes were made', 'OK', { duration: 2500 });
  }
}