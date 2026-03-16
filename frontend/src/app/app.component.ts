import { HttpClient } from '@angular/common/http';
import { NgIf } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { catchError, of } from 'rxjs';

type AccountResponse = {
  id: string;
  balance: number;
  currency: string;
};

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  private readonly http = inject(HttpClient);

  account: AccountResponse | null = null;
  error: string | null = null;

  ngOnInit(): void {
    this.http.get<AccountResponse>('/api/account').pipe(
      catchError(() => {
        this.error = 'Nie udało się połączyć z API';
        return of(null);
      })
    ).subscribe((response) => {
      this.account = response;
    });
  }
}
