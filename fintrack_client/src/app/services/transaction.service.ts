import { AxiosError } from 'axios';
import { api } from '../http/api';
import { getCustomCookie } from '../utils/cookies';
import {
	TransactionRequest,
	TransactionResponse
} from '../models/transaction.model';

export async function createTransaction(data: TransactionRequest) {
	try {
		const token = await getCustomCookie('access_token');
		const response = await api.post<TransactionResponse>(
			'/transactions',
			data,
			{
				headers: { Authorization: `Bearer ${token}` }
			}
		);
		return response.data;
	} catch (error) {
		if (error instanceof AxiosError) {
			throw error.response?.data;
		}
	}
}
