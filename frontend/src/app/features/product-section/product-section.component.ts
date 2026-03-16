import { NgFor, NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { catchError, map, of } from 'rxjs';
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
  editingProductId: string | null = null;
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
    this.editingProductId = null;
    this.formModel = { name: '' };
    this.submitError = null;
  }

  openEditForm(product: ProductResponse): void {
    this.isFormOpen = true;
    this.editingProductId = product.id;
    this.formModel = { name: product.name };
    this.submitError = null;
  }

  closeForm(): void {
    this.isFormOpen = false;
    this.editingProductId = null;
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
    const request$ = this.editingProductId
      ? this.productsApi.update(this.editingProductId, request)
      : this.productsApi.create(request);

    request$.pipe(
      catchError(() => {
        this.submitError = 'Failed to save product';
        this.isSubmitting = false;
        return of(null);
      })
    ).subscribe((savedProduct) => {
      if (!savedProduct) {
        return;
      }

      if (this.editingProductId) {
        this.products = this.products.map((product) =>
          product.id === savedProduct.id ? savedProduct : product
        );
      } else {
        this.products = [savedProduct, ...this.products];
      }

      this.isSubmitting = false;
      this.closeForm();
    });
  }

  deleteProduct(productId: string): void {
    this.submitError = null;

    this.productsApi.delete(productId).pipe(
      map(() => true),
      catchError((error: HttpErrorResponse) => {
        this.submitError = error.status === 409
          ? 'Cannot delete product because it is assigned to campaigns.'
          : 'Failed to delete product';
        return of(false);
      })
    ).subscribe((deleted) => {
      if (!deleted) {
        return;
      }

      this.products = this.products.filter((product) => product.id !== productId);
      if (this.editingProductId === productId) {
        this.closeForm();
      }
    });
  }

  private loadProducts(): void {
    this.productsApi.getAll().pipe(
      catchError(() => {
        this.loadError = 'Failed to load products';
        return of([] as ProductResponse[]);
      })
    ).subscribe((products) => {
      this.products = products;
    });
  }
}
