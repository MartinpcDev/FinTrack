import { Category } from './category.model';

export interface TransactionRequest {
	type: string;
	amount: number;
	category_id: number;
}

export interface Transaction {
	id: number;
	type: string;
	amount: number;
	category: Category;
}

export interface TransactionResponse {
	message: string;
	data: Transaction;
}
