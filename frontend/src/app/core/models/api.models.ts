export type CampaignStatus = 'ON' | 'OFF';

export type ProductResponse = {
  id: string;
  name: string;
  createdAt: string;
  updatedAt: string;
};

export type ProductUpsertRequest = {
  name: string;
};

export type TownResponse = {
  id: string;
  name: string;
};

export type KeywordResponse = {
  id: string;
  value: string;
};

export type CampaignResponse = {
  id: string;
  productId: string;
  productName: string;
  name: string;
  keywords: KeywordResponse[];
  bidAmount: number;
  campaignFund: number;
  status: CampaignStatus;
  town: TownResponse;
  radiusKm: number;
  createdAt: string;
  updatedAt: string;
};

export type CampaignCreateRequest = {
  productId: string;
  name: string;
  keywordIds: string[];
  bidAmount: number;
  campaignFund: number;
  status: CampaignStatus;
  townId: string;
  radiusKm: number;
};

export type CampaignMutationResponse = {
  campaign: CampaignResponse;
  accountBalance: number;
};

export type AccountResponse = {
  id: string;
  balance: number;
  currency: string;
  createdAt: string;
  updatedAt: string;
};

export type ErrorResponse = {
  code: string;
  message: string;
  details: string[];
  timestamp: string;
};
