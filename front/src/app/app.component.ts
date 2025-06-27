import { Component } from "@angular/core";
import { RouterModule, Router } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { AuthService } from "./auth/auth.service";
import { CommonModule } from '@angular/common';
import { CartService } from './carte/data-access/cart.service';

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    SplitterModule,
    ToolbarModule,
    PanelMenuComponent
  ]
})
export class AppComponent {
  title = "ALTEN SHOP";

  constructor(
    public authService: AuthService,
    public cartService: CartService,
    private router: Router
  ) {}

  logout(): void {
    this.authService.logout(); // <-- propre maintenant
    this.router.navigate(['/login']); // redirection propre
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated(); // <-- utilise le service, pas juste localStorage
  }

  goToCart(): void {
    this.router.navigate(['/cart']);
  }
}