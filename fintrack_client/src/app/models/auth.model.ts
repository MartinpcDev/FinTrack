interface LoginRequest {
	username: string;
	password: string;
}

interface AuthResponse {
	access_token: string;
	refresh_token: string;
	message: string;
}

interface RegisterRequest {
	name: string;
	username: string;
	password: string;
	email: string;
}

interface RegisterResponse {
	message: string;
}

interface RefreshRequest {
	refresh_token: string;
}

interface ProfileResponse {
	id: number;
	name: string;
	username: string;
	email: string;
}
