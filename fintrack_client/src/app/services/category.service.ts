import { api } from '../http/api';
import { Category } from '../models/category.model';
import { getCustomCookie } from '../utils/cookies';

export async function allCategories() {
	const token = await getCustomCookie('access_token');
	const response = await api.get<Category[]>('/categories', {
		headers: { Authorization: `Bearer ${token}` }
	});

	return response.data;
}
