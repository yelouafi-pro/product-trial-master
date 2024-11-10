import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { CartItem } from 'app/shared/data-access/cart.model';
import { CartService } from 'app/shared/services/cart.service';
import { MessagesModule } from 'primeng/messages';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, MessagesModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  totalPrice: number = 0;

  constructor(private cartService: CartService, private messageService: MessageService) {}

  ngOnInit(): void {
    this.cartService.cartItems$.subscribe((items: CartItem[]) => {
      this.cartItems = items;
      this.totalPrice = this.cartService.getTotalPrice();
    });
  }

  removeFromCart(productId: number): void {
    this.cartService.removeFromCart(productId);
  }

  increaseQuantity(productId: number): void {
    const cartItem = this.cartService.getCartItemByProductId(productId);
  
    if (cartItem) {
      const stockQuantity = cartItem.product.quantity;
  
      if (cartItem.quantity < stockQuantity) {
        this.cartService.changeQuantity(productId, 1);
      } else {
        this.messageService.add({
          severity: 'warn', 
          summary: 'Warning', 
          detail: 'Cannot exceed stock quantity!',
          life: 3000 
        });
      }
    }
  }
  
  decreaseQuantity(productId: number): void {
    const cartItem = this.cartService.getCartItemByProductId(productId);
  
    if (cartItem && cartItem.quantity > 1) {
      this.cartService.changeQuantity(productId, -1);
    } else {
      this.messageService.add({
        severity: 'warn', 
        summary: 'Warning', 
        detail: 'You cannot decrease the quantity below 1.',
        life: 3000 
      });
    }
  }
      

}