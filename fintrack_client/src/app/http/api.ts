import axios from 'axios';
import {
	clearCookie,
	getCustomCookie,
	setCustomCookie
} from '../utils/cookies';
import { redirect } from 'next/navigation';

export const api = axios.create({
	baseURL: process.env.NEXT_PUBLIC_BASE_URL
});

api.interceptors.response.use(
	response => response,
	async error => {
		if (error.response.status === 403) {
			const refreshToken = await getCustomCookie('refresh_token');

			if (!refreshToken) {
				redirect('/');
				return Promise.reject(error);
			}

			try {
				const { data } = await api.post<AuthResponse>('/auth/refresh-token', {
					refresh_token: refreshToken
				});

				await setCustomCookie('access_token', data.access_token);
				await setCustomCookie('refresh_token', data.refresh_token);
				error.config.headers['Authorization'] = `Bearer ${data.access_token}`;
				return api(error.config);
			} catch (refreshError) {
				await clearCookie('access_token');
				await clearCookie('refresh_token');
				redirect('/');
				return Promise.reject(refreshError);
			}
		}

		return Promise.reject(error);
	}
);
