import axios from 'axios';
import {
	clearAllCookies,
	getCustomCookie,
	setCustomCookie
} from '../utils/cookies';
import { redirect } from 'next/navigation';
import { refresh } from '../services/auth.service';

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
			}

			try {
				const data = await refresh({ refresh_token: String(refreshToken) });
				console.log(data);

				await setCustomCookie('access_token', data!.access_token);
				await setCustomCookie('refresh_token', data!.refresh_token);

				error.config.headers['Authorization'] = `Bearer ${data?.access_token}`;
				return api(error.config);
			} catch (refreshError) {
				await clearAllCookies();
				redirect('/');
			}
		}

		return Promise.reject(error);
	}
);
