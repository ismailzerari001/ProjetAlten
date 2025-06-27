import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // Authentification
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/token`, credentials);
  }

  // Enregistrement
  register(user: { username: string; firstname: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/account`, user);
  }

  // Vérifie si un token valide existe
  isAuthenticated(): boolean {
    const token = sessionStorage.getItem('token');
    if (!token) return false;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const now = Math.floor(Date.now() / 1000);
      return payload.exp && payload.exp > now;
    } catch (e) {
      return false; // Token invalide
    }
  }

  // Supprime le token à la déconnexion
  logout(): void {
    sessionStorage.removeItem('token');
  }

  // Récupère l’email de l’utilisateur connecté (depuis le JWT)
  getCurrentUserEmail(): string {
    const token = sessionStorage.getItem('token');
    if (!token) return '';
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.sub || ''; // ou payload.email selon le contenu de ton token
    } catch (e) {
      return '';
    }
  }
}