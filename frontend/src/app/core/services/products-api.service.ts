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
}
