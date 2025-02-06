interface LoginRequest {
	username: string;
	password: string;
}

interface AuthResponse {
	access_token: string;
	refresh_token: string;
	message: string;
}
