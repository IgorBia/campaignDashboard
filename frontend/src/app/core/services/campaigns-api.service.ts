import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  CampaignCreateRequest,
  CampaignMutationResponse,
  CampaignResponse
} from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class CampaignsApiService {
  private readonly http = inject(HttpClient);

  getAll(): Observable<CampaignResponse[]> {
    return this.http.get<CampaignResponse[]>('/api/campaigns');
  }

  create(request: CampaignCreateRequest): Observable<CampaignMutationResponse> {
    return this.http.post<CampaignMutationResponse>('/api/campaigns', request);
  }
}
