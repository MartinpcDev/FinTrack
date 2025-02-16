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
	createdAt: string;
}

export type TransactionUpdate = Omit<Transaction, 'id'>;

export interface TransactionResponse {
	message: string;
	data: Transaction;
}

export interface TransactionPageData {
	content: Transaction[];
	page: number;
	size: number;
	total: number;
	total_pages: number;
}

export interface DeleteResponse {
	message: string;
}
