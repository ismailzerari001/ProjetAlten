import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import { LoginComponent } from "./auth/login.component";
import { RegisterComponent } from "./auth/register.component";
import { AuthGuard } from "./auth.guard";
import { CART_ROUTES } from './carte/cart.routes';


export const APP_ROUTES: Routes = [
  {
    path: "home",
    component: HomeComponent,
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES),
    canActivate: [AuthGuard]
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  ...CART_ROUTES,
  { path: "", redirectTo: "login", pathMatch: "full" },
{
  path: "contact",
  loadComponent: () =>
    import("./contact/contact.component").then(m => ({ default: m.ContactComponent }))
}

  
];
