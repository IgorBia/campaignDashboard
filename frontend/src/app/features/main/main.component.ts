import { NgIf } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { catchError, of } from 'rxjs';
import { AccountApiService } from '../../core/services/account-api.service';
import { AccountResponse } from '../../core/models/api.models';
import { ProductSectionComponent } from '../product-section/product-section.component';
import { CampaignSectionComponent } from '../campaign-section/campaign-section.component';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [NgIf, ProductSectionComponent, CampaignSectionComponent],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit {
  private readonly accountApi = inject(AccountApiService);

  account: AccountResponse | null = null;
  accountError: string | null = null;

  ngOnInit(): void {
    this.loadAccount();
  }

  onAccountBalanceChanged(nextBalance: number): void {
    if (!this.account) {
      return;
    }

    this.account = {
      ...this.account,
      balance: nextBalance
    };
  }

  private loadAccount(): void {
    this.accountApi.getAccount().pipe(
      catchError(() => {
        this.accountError = 'Failed to load account balance';
        return of(null);
      })
    ).subscribe((account) => {
      this.account = account;
    });
  }
}
