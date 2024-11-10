import { Component, OnInit, inject, signal } from "@angular/core";
import { CommonModule } from '@angular/common';
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { CartService } from "app/shared/services/cart.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { PaginatorModule } from 'primeng/paginator';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, CommonModule, PaginatorModule, ToastModule],
})
export class ProductListComponent implements OnInit {

  private readonly productsService = inject(ProductsService);
  private readonly cartService = inject(CartService);

  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

  public rows = 5;
  public first = 0;

  public selectedCategory: string = "";
  public productName: string = "";
  public priceMin: number | null = null;
  public priceMax: number | null = null;
  public filteredProducts: Product[] = [];

  selectedProduct: Product | null = null;
  desiredQuantity: number = 1;
  isDialogQuantity: boolean = false;

  constructor(private messageService: MessageService) {}

  ngOnInit() {
    this.loadProducts();
  }

  onPageChange(event: any) {
    this.first = event.first;
    this.rows = event.rows;
  }
  
  addToCart(product: Product): void {
    this.cartService.addToCart(product, 1);
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe(() => {
      this.loadProducts();
      this.showMessage('Produit supprimé avec succès !', 'success', 'Succès')
    });
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe(() => {
        this.loadProducts();
        this.showMessage('Produit créé avec succès', 'success', 'Succès')
      });
    } else {
      this.productsService.update(product).subscribe(() => {
        this.loadProducts();
        this.showMessage('Produit mis à jour avec succès', 'success', 'Succès')
      });
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  public uniqueCategories: string[] = [];
  
  private loadProducts() {
    this.productsService.get().subscribe(() => {
      this.filteredProducts = this.products();
      this.uniqueCategories = [...new Set(this.products().map(product => product.category))];
    });
  }

  showQuantityDialog(product: Product): void {
    console.log(JSON.stringify(product))
    if (product.inventoryStatus === 'OUTOFSTOCK') {
      this.messageService.add({
        severity: 'error',
        summary: 'Produit indisponible',
        detail: 'Ce produit est en rupture de stock et ne peut pas être ajouté au panier.',
        life: 3000
      });
    } else {
      this.selectedProduct = product;
      this.desiredQuantity = 1;
      this.isDialogQuantity = true;
    }
    
  }

  cancelQuantityDialog(): void {
    this.isDialogQuantity = false;
    this.selectedProduct = null;
  }

  private showMessage(detail: string, status:string, summary:string) {
    this.messageService.add({
      severity: status, 
      summary: summary, 
      detail: detail,
      life: 3000 
    });
  }
  show() {
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Message Content' });
}

  addToCartWithQuantity(): void {
    if (this.selectedProduct) {
      const quantity = this.desiredQuantity;
      if (quantity > 0 && quantity <= this.selectedProduct.quantity) {
        this.cartService.addToCart(this.selectedProduct, quantity);
        this.isDialogQuantity = false;
        this.selectedProduct = null;
      } else {
        this.showMessage('Quantité en stock dépassée !', 'warn', 'Warning')
      }
    }
  }

  public applyFilters() {
    this.filteredProducts = this.products().filter(product => {
      const matchesCategory = this.selectedCategory ? product.category === this.selectedCategory : true;
      const matchesName = this.productName ? product.name.toLowerCase().includes(this.productName.toLowerCase()) : true;
      const matchesMinPrice = this.priceMin !== null ? product.price >= this.priceMin : true;
      const matchesMaxPrice = this.priceMax !== null ? product.price <= this.priceMax : true;

      return matchesCategory && matchesName && matchesMinPrice && matchesMaxPrice;
    });
  }
}