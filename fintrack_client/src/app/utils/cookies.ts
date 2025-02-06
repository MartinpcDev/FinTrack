'use server';

import { cookies } from 'next/headers';

export async function getCustomCookie(name: string) {
	const cookieStore = await cookies();
	const token = cookieStore.get(name);
	return token?.value;
}

export async function clearCookie(name: string) {
	const cookieStore = await cookies();
	cookieStore.delete(name);
}

export async function setCustomCookie(name: string, value: string) {
	const cookieStore = await cookies();
	cookieStore.set(name, value);
}
