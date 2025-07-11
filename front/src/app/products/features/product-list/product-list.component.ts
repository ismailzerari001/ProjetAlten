import { Component, OnInit, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { CommonModule } from "@angular/common"; 
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { CartService } from '../../../carte/data-access/cart.service'; 
import { AuthService } from '../../../auth/auth.service'; 
import { FormsModule } from '@angular/forms'; 

const emptyProduct: Product = {
  id: null,
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
  imports: [FormsModule,CommonModule, DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);
  private readonly authService = inject(AuthService); 
public searchTerm: string = '';

public filteredProducts: Product[] = []; 
  public readonly products = this.productsService.products;
  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

  private readonly cartService = inject(CartService);

  ngOnInit() {
  this.productsService.get().subscribe(() => {
    this.filteredProducts = this.products(); // Initialisation
  });
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
    if (product.id !== null) {
      this.productsService.delete(product.id).subscribe();
    }
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe();
    } else {
      this.productsService.update(product).subscribe();
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  public addToCart(product: Product) {
    this.cartService.addToCart(product);
  }

  isAdminUser(): boolean {
    return this.authService.getCurrentUserEmail() === 'admin@admin.com';
  }
  onSearch() {
  const term = this.searchTerm.toLowerCase();
  this.filteredProducts = this.products().filter(product =>
    product.name.toLowerCase().includes(term)
  );
}
}