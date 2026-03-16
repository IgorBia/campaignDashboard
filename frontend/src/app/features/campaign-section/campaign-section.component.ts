import { NgFor, NgIf } from '@angular/common';
import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { catchError, of } from 'rxjs';
import {
  CampaignCreateRequest,
  CampaignResponse,
  CampaignStatus,
  KeywordResponse,
  ProductResponse,
  TownResponse
} from '../../core/models/api.models';
import { CampaignsApiService } from '../../core/services/campaigns-api.service';
import { DictionariesApiService } from '../../core/services/dictionaries-api.service';
import { ProductsApiService } from '../../core/services/products-api.service';

type CampaignFormModel = {
  productId: string;
  name: string;
  bidAmount: number;
  campaignFund: number;
  status: CampaignStatus;
  townId: string;
  radiusKm: number;
};

@Component({
  selector: 'app-campaign-section',
  standalone: true,
  imports: [NgIf, NgFor, FormsModule],
  templateUrl: './campaign-section.component.html',
  styleUrl: './campaign-section.component.css'
})
export class CampaignSectionComponent implements OnInit {
  private readonly campaignsApi = inject(CampaignsApiService);
  private readonly productsApi = inject(ProductsApiService);
  private readonly dictionariesApi = inject(DictionariesApiService);

  @Output() accountBalanceChanged = new EventEmitter<number>();

  isFormOpen = false;
  formModel: CampaignFormModel = this.getEmptyFormModel();
  campaigns: CampaignResponse[] = [];
  products: ProductResponse[] = [];
  towns: TownResponse[] = [];
  keywordQuery = '';
  keywordSuggestions: KeywordResponse[] = [];
  selectedKeywords: KeywordResponse[] = [];
  isSubmitting = false;
  loadError: string | null = null;
  submitError: string | null = null;

  ngOnInit(): void {
    this.loadCampaigns();
  }

  openCreateForm(): void {
    this.isFormOpen = true;
    this.formModel = this.getEmptyFormModel();
    this.selectedKeywords = [];
    this.keywordSuggestions = [];
    this.keywordQuery = '';
    this.submitError = null;
    this.loadProducts();
    this.loadTowns();
  }

  closeForm(): void {
    this.isFormOpen = false;
    this.submitError = null;
  }

  submitForm(): void {
    if (this.selectedKeywords.length === 0) {
      return;
    }

    const request: CampaignCreateRequest = {
      productId: this.formModel.productId.trim(),
      name: this.formModel.name.trim(),
      keywordIds: this.selectedKeywords.map((keyword) => keyword.id),
      bidAmount: this.formModel.bidAmount,
      campaignFund: this.formModel.campaignFund,
      status: this.formModel.status,
      townId: this.formModel.townId.trim(),
      radiusKm: this.formModel.radiusKm
    };

    this.isSubmitting = true;
    this.campaignsApi.create(request).pipe(
      catchError(() => {
        this.submitError = 'Nie udało się zapisać kampanii';
        this.isSubmitting = false;
        return of(null);
      })
    ).subscribe((mutationResponse) => {
      if (!mutationResponse) {
        return;
      }

      this.campaigns = [mutationResponse.campaign, ...this.campaigns];
      this.accountBalanceChanged.emit(mutationResponse.accountBalance);
      this.isSubmitting = false;
      this.closeForm();
    });
  }

  onKeywordQueryChange(): void {
    const query = this.keywordQuery.trim();
    if (!query) {
      this.keywordSuggestions = [];
      return;
    }

    this.dictionariesApi.searchKeywords(query).pipe(
      catchError(() => of([] as KeywordResponse[]))
    ).subscribe((keywords) => {
      const selectedIds = new Set(this.selectedKeywords.map((keyword) => keyword.id));
      this.keywordSuggestions = keywords.filter((keyword) => !selectedIds.has(keyword.id));
    });
  }

  addKeyword(keyword: KeywordResponse): void {
    if (this.selectedKeywords.some((selectedKeyword) => selectedKeyword.id === keyword.id)) {
      return;
    }

    this.selectedKeywords = [...this.selectedKeywords, keyword];
    this.keywordSuggestions = this.keywordSuggestions.filter((suggestion) => suggestion.id !== keyword.id);
    this.keywordQuery = '';
  }

  removeKeyword(keywordId: string): void {
    this.selectedKeywords = this.selectedKeywords.filter((keyword) => keyword.id !== keywordId);
  }

  private getEmptyFormModel(): CampaignFormModel {
    return {
      productId: '',
      name: '',
      bidAmount: 0.01,
      campaignFund: 0.01,
      status: 'ON',
      townId: '',
      radiusKm: 1
    };
  }

  private loadCampaigns(): void {
    this.campaignsApi.getAll().pipe(
      catchError(() => {
        this.loadError = 'Nie udało się pobrać kampanii';
        return of([] as CampaignResponse[]);
      })
    ).subscribe((campaigns) => {
      this.campaigns = campaigns;
    });
  }

  private loadProducts(): void {
    this.productsApi.getAll().pipe(
      catchError(() => of([] as ProductResponse[]))
    ).subscribe((products) => {
      this.products = products;
      if (!this.formModel.productId && products.length > 0) {
        this.formModel.productId = products[0].id;
      }
    });
  }

  private loadTowns(): void {
    this.dictionariesApi.getTowns().pipe(
      catchError(() => of([] as TownResponse[]))
    ).subscribe((towns) => {
      this.towns = towns;
      if (!this.formModel.townId && towns.length > 0) {
        this.formModel.townId = towns[0].id;
      }
    });
  }
}
