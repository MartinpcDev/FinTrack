import { AxiosError } from 'axios';
import { api } from '../http/api';
import {
	clearAllCookies,
	clearCookie,
	getCustomCookie,
	setCustomCookie
} from '../utils/cookies';

export async function login(data: LoginRequest) {
	try {
		const response = await api.post<AuthResponse>('/auth/login', data);
		await setCustomCookie('refresh_token', response.data.refresh_token);
		await setCustomCookie('access_token', response.data.access_token);
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
		const accessToken = await getCustomCookie('access_token');
		const response = await api.get<ProfileResponse>('/auth/profile', {
			headers: { Authorization: `Bearer ${accessToken}` }
		});
		return response.data;
	} catch (error) {
		if (error instanceof AxiosError) {
			throw error.response?.data;
		}
	}
}

export async function logoutSession() {
	await clearAllCookies();
}
