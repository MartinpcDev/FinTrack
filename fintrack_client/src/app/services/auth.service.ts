import { AxiosError } from 'axios';
import { api } from '../http/api';
import { getCustomCookie, setCustomCookie } from '../utils/cookies';

export async function login(data: LoginRequest) {
	try {
		const response = await api.post<AuthResponse>('/auth/login', data);
		await setCustomCookie('access_token', response.data.access_token);
		await setCustomCookie('refresh_token', response.data.refresh_token);
		return response.data;
	} catch (error) {
		if (error instanceof AxiosError) {
			throw error.response?.data;
		}
	}
}

export async function register(data: RegisterRequest) {
	try {
		const response = await api.post<RegisterResponse>('/auth/register', data);
		return response.data;
	} catch (error) {
		if (error instanceof AxiosError) {
			throw error.response?.data;
		}
	}
}

export async function refresh(data: RefreshRequest) {
	try {
		const response = await api.post<AuthResponse>('/auth/refresh-token', data);
		return response.data;
	} catch (error) {
		if (error instanceof AxiosError) {
			throw error.response?.data;
		}
	}
}

export async function profile() {
	try {
		const refreshToken = await getCustomCookie('refresh_token');
		const response = await api.get<ProfileResponse>('/auth/profile', {
			headers: { Authorization: `Bearer ${refreshToken}` }
		});
		return response.data;
	} catch (error) {
		if (error instanceof AxiosError) {
			throw error.response?.data;
		}
	}
}
