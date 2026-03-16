import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { KeywordResponse, TownResponse } from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class DictionariesApiService {
  private readonly http = inject(HttpClient);

  getTowns(): Observable<TownResponse[]> {
    return this.http.get<TownResponse[]>('/api/dictionaries/towns');
  }

  searchKeywords(query: string): Observable<KeywordResponse[]> {
    return this.http.get<KeywordResponse[]>(`/api/dictionaries/keywords?query=${encodeURIComponent(query)}`);
  }
}
