import { Product } from "app/products/data-access/product.model";

export interface CartItem {
    product: Product;
    quantity: number; 
}