import { NgFor, NgIf } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { catchError, of } from 'rxjs';
import { ProductResponse, ProductUpsertRequest } from '../../core/models/api.models';
import { ProductsApiService } from '../../core/services/products-api.service';

@Component({
  selector: 'app-product-section',
  standalone: true,
  imports: [NgIf, NgFor, FormsModule],
  templateUrl: './product-section.component.html',
  styleUrl: './product-section.component.css'
})
export class ProductSectionComponent implements OnInit {
  private readonly productsApi = inject(ProductsApiService);

  isFormOpen = false;
  formModel: ProductUpsertRequest = { name: '' };
  products: ProductResponse[] = [];
  isSubmitting = false;
  loadError: string | null = null;
  submitError: string | null = null;

  ngOnInit(): void {
    this.loadProducts();
  }

  openCreateForm(): void {
    this.isFormOpen = true;
    this.formModel = { name: '' };
    this.submitError = null;
  }

  closeForm(): void {
    this.isFormOpen = false;
    this.submitError = null;
  }

  submitForm(): void {
    const trimmedName = this.formModel.name.trim();
    if (!trimmedName) {
      return;
    }

    const request: ProductUpsertRequest = {
      name: trimmedName
    };

    this.isSubmitting = true;
    this.productsApi.create(request).pipe(
      catchError(() => {
        this.submitError = 'Nie udało się zapisać produktu';
        this.isSubmitting = false;
        return of(null);
      })
    ).subscribe((createdProduct) => {
      if (!createdProduct) {
        return;
      }

      this.products = [createdProduct, ...this.products];
      this.isSubmitting = false;
      this.closeForm();
    });
  }

  private loadProducts(): void {
    this.productsApi.getAll().pipe(
      catchError(() => {
        this.loadError = 'Nie udało się pobrać produktów';
        return of([] as ProductResponse[]);
      })
    ).subscribe((products) => {
      this.products = products;
    });
  }
}
