import { Component } from "@angular/core";
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { ButtonModule } from "primeng/button";

@Component({
  standalone: true,
  selector: "app-contact",
  imports: [CommonModule, ReactiveFormsModule, ButtonModule],
  templateUrl: "./contact.component.html",
 styleUrls: ["./contact.component.scss"], 

})
export class ContactComponent {
  form: FormGroup;
  successMessage = "";

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      email: ["", [Validators.required, Validators.email]],
      message: ["", [Validators.required, Validators.maxLength(300)]],
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this.successMessage = "Demande de contact envoyée avec succès";
      this.form.reset();
    }
  }
}