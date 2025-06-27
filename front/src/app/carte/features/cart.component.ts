import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ButtonModule } from "primeng/button";
import { CartService } from "../data-access/cart.service";

@Component({
  selector: "app-cart",
  standalone: true,
  imports: [CommonModule, ButtonModule],
  templateUrl: "./cart.component.html",
})
export class CartComponent {
  constructor(public cartService: CartService) {}

  removeProduct(id: number) {
    this.cartService.removeFromCart(id);
  }
}
