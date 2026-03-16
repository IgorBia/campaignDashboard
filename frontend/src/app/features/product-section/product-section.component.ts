import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

type ProductUpsertRequest = {
  name: string;
};

@Component({
  selector: 'app-product-section',
  standalone: true,
  imports: [NgIf, FormsModule],
  templateUrl: './product-section.component.html',
  styleUrl: './product-section.component.css'
})
export class ProductSectionComponent {
  isFormOpen = false;
  isEditMode = false;
  formModel: ProductUpsertRequest = { name: '' };
  submittedProductRequest: ProductUpsertRequest | null = null;

  openCreateForm(): void {
    this.isFormOpen = true;
    this.isEditMode = false;
    this.formModel = { name: '' };
  }

  openEditForm(): void {
    this.isFormOpen = true;
    this.isEditMode = true;
    this.formModel = { name: 'Przykładowy produkt' };
  }

  closeForm(): void {
    this.isFormOpen = false;
  }

  submitForm(): void {
    const trimmedName = this.formModel.name.trim();
    if (!trimmedName) {
      return;
    }

    this.submittedProductRequest = {
      name: trimmedName
    };

    this.closeForm();
  }

  deleteProduct(): void {
  }
}
