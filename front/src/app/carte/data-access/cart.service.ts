import { Injectable, signal } from "@angular/core";
import { Product } from "../../products/data-access/product.model";

@Injectable({ providedIn: 'root' })
export class CartService {
  private readonly items = signal<Product[]>([]);

  get cartItems() {
    return this.items.asReadonly();
  }

  addToCart(product: Product) {
    this.items.update(current => [...current, product]);
  }

  removeFromCart(productId: number) {
    this.items.update(current => current.filter(p => p.id !== productId));
  }

  clearCart() {
    this.items.set([]);
  }

  count(): number {
    return this.items().length;
  }

  getTotal(): number {
    return this.items().reduce((total, item) => total + item.price, 0);
  }
}
