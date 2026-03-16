import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductResponse, ProductUpsertRequest } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class ProductsApiService {
  private readonly http = inject(HttpClient);

  getAll(): Observable<ProductResponse[]> {
    return this.http.get<ProductResponse[]>('/api/products');
  }

  create(request: ProductUpsertRequest): Observable<ProductResponse> {
    return this.http.post<ProductResponse>('/api/products', request);
  }

  update(id: string, request: ProductUpsertRequest): Observable<ProductResponse> {
    return this.http.put<ProductResponse>(`/api/products/${id}`, request);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`/api/products/${id}`);
  }
}
