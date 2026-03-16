import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

type CampaignStatus = 'ON' | 'OFF';

type CampaignUpsertRequest = {
  productId: string;
  name: string;
  keywordIds: string[];
  bidAmount: number;
  campaignFund: number;
  status: CampaignStatus;
  townId: string;
  radiusKm: number;
};

type CampaignFormModel = {
  productId: string;
  name: string;
  keywordIdsRaw: string;
  bidAmount: number;
  campaignFund: number;
  status: CampaignStatus;
  townId: string;
  radiusKm: number;
};

@Component({
  selector: 'app-campaign-section',
  standalone: true,
  imports: [NgIf, FormsModule],
  templateUrl: './campaign-section.component.html',
  styleUrl: './campaign-section.component.css'
})
export class CampaignSectionComponent {
  isFormOpen = false;
  isEditMode = false;
  formModel: CampaignFormModel = this.getEmptyFormModel();
  submittedCampaignRequest: CampaignUpsertRequest | null = null;

  openCreateForm(): void {
    this.isFormOpen = true;
    this.isEditMode = false;
    this.formModel = this.getEmptyFormModel();
  }

  openEditForm(): void {
    this.isFormOpen = true;
    this.isEditMode = true;
    this.formModel = {
      productId: '00000000-0000-0000-0000-000000000001',
      name: 'Przykładowa kampania',
      keywordIdsRaw: '00000000-0000-0000-0000-000000000001',
      bidAmount: 1,
      campaignFund: 100,
      status: 'ON',
      townId: '00000000-0000-0000-0000-000000000001',
      radiusKm: 5
    };
  }

  closeForm(): void {
    this.isFormOpen = false;
  }

  submitForm(): void {
    const keywordIds = this.formModel.keywordIdsRaw
      .split(',')
      .map((id) => id.trim())
      .filter((id) => id.length > 0);

    if (keywordIds.length === 0) {
      return;
    }

    this.submittedCampaignRequest = {
      productId: this.formModel.productId.trim(),
      name: this.formModel.name.trim(),
      keywordIds,
      bidAmount: this.formModel.bidAmount,
      campaignFund: this.formModel.campaignFund,
      status: this.formModel.status,
      townId: this.formModel.townId.trim(),
      radiusKm: this.formModel.radiusKm
    };

    this.closeForm();
  }

  deleteCampaign(): void {
  }

  private getEmptyFormModel(): CampaignFormModel {
    return {
      productId: '',
      name: '',
      keywordIdsRaw: '',
      bidAmount: 0.01,
      campaignFund: 0.01,
      status: 'ON',
      townId: '',
      radiusKm: 1
    };
  }
}
