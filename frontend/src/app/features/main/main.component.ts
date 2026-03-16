import { Component } from '@angular/core';
import { ProductSectionComponent } from '../product-section/product-section.component';
import { CampaignSectionComponent } from '../campaign-section/campaign-section.component';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [ProductSectionComponent, CampaignSectionComponent],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {
}
