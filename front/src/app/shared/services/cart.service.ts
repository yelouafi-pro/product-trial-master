import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product } from '../../products/data-access/product.model';
import { CartItem } from '../data-access/cart.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItemsSubject = new BehaviorSubject<CartItem[]>([]);
  public cartItems$ = this.cartItemsSubject.asObservable();

  constructor() {}

  addToCart(product: Product, quantity: number): void {
    const currentItems = this.cartItemsSubject.value;
    const existingCartItem = currentItems.find(item => item.product.id === product.id);
    
    if (existingCartItem) {
      existingCartItem.quantity += quantity;
    } else {
      currentItems.push({ product, quantity });
    }

    this.cartItemsSubject.next(currentItems);
  }

  removeFromCart(productId: number): void {
    const currentItems = this.cartItemsSubject.value.filter(item => item.product.id !== productId);
    this.cartItemsSubject.next(currentItems);
  }

  getTotalPrice(): number {
    return this.cartItemsSubject.value.reduce((total, item) => total + (item.product.price * item.quantity), 0);
  }

  changeQuantity(productId: number, quantityChange: number): void {
    const currentItems = this.cartItemsSubject.value;
    const cartItem = currentItems.find(item => item.product.id === productId);

    if (cartItem) {
      cartItem.quantity = Math.max(1, cartItem.quantity + quantityChange);
      this.cartItemsSubject.next(currentItems);
    }
  }

  getCartProductCount(): number {
    // return this.cartItemsSubject.value.reduce((total, item) => total + item.quantity, 0);
    return this.cartItemsSubject.value.length;
  }

  getProductQuantity(productId: number): number {
    const cartItem = this.cartItemsSubject.value.find(item => item.product.id === productId);
    return cartItem ? cartItem.quantity : 0;
  }

  getCartItemByProductId(productId: number): CartItem | undefined {
    return this.cartItemsSubject.value.find(item => item.product.id === productId);
  }
}
