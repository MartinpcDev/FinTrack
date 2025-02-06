import { api } from '../http/api';

export async function login(data: LoginRequest) {
	const response = await api.post<AuthResponse>('/auth/login', data);
	return response.data;
}
