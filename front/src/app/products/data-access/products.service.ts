import { Injectable, inject, signal } from "@angular/core";
import { Product } from "./product.model";
import { HttpClient } from "@angular/common/http";
import { catchError, Observable, of, tap } from "rxjs";
import { environment } from "../../../environments/environment";
import { MessageService } from "primeng/api";

@Injectable({
    providedIn: "root"
}) export class ProductsService {

    constructor(private messageService: MessageService) {}

    private readonly http = inject(HttpClient);
    private readonly path = `${environment.apiUrl}/api/products`;
    
    private readonly _products = signal<Product[]>([]);

    public readonly products = this._products.asReadonly();

    public get(): Observable<Product[]> {
        return this.http.get<Product[]>(this.path).pipe(
            catchError((error) => {
                return this.http.get<Product[]>("assets/products.json");
            }),
            tap((products) => this._products.set(products)),
        );
    }

    public create(product: Product): Observable<boolean> {
        return this.http.post<boolean>(this.path, product).pipe(
          catchError((error) => {
            if (error.error?.message?.includes(`Violation d'index unique`)) {
              this.messageService.add({
                severity: 'error',
                summary: 'Erreur',
                detail: 'Le produit existe déjà. Veuillez entrer un produit unique.',
              });
            } else {
              this.messageService.add({
                severity: 'error',
                summary: 'Erreur',
                detail: 'Une erreur est survenue lors de la création du produit.',
              });
            }
            throw error;
          }),
          tap(() => {
            this._products.update((products) => [product, ...products]);
          })
        );
      }

    public update(product: Product): Observable<boolean> {
        return this.http.patch<boolean>(`${this.path}/${product.id}`, product).pipe(
            catchError((error) => {
                if (error.error?.message?.includes(`Violation d'index unique`)) {
                    this.messageService.add({
                      severity: 'error',
                      summary: 'Erreur',
                      detail: 'Le produit existe déjà. Veuillez entrer un produit unique.',
                    });
                  } else {
                    this.messageService.add({
                      severity: 'error',
                      summary: 'Erreur',
                      detail: 'Une erreur est survenue lors de la création du produit.',
                    });
                  }
                  throw error;
            }),
            tap(() => this._products.update(products => {
                return products.map(p => p.id === product.id ? product : p)
            })),
        );
    }

    public delete(productId: number): Observable<boolean> {
        return this.http.delete<boolean>(`${this.path}/${productId}`).pipe(
            catchError(() => {
                return of(true);
            }),
            tap(() => this._products.update(products => products.filter(product => product.id !== productId))),
        );
    }
}