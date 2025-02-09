import { NextRequest, NextResponse } from 'next/server';
import { profile } from './app/services/auth.service';

export async function middleware(request: NextRequest) {
	const user = await profile();

	if (!user) {
		return NextResponse.redirect(new URL('/', request.url));
	}

	return NextResponse.next();
}

export const config = {
	matcher: ['/dashboard/:path*']
};
