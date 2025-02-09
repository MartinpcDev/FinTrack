import type { Metadata } from 'next';
import { Geist, Geist_Mono } from 'next/font/google';
import './globals.css';
import { Toaster } from 'sonner';
import { montserrat } from './utils/fonts';

export const metadata: Metadata = {
	title: 'Fintrack Page',
	description: 'Aplicacion web para el control de finanzas personales'
};

export default function RootLayout({
	children
}: Readonly<{
	children: React.ReactNode;
}>) {
	return (
		<html lang='es'>
			<body className={`${montserrat.className} antialiased`}>
				<Toaster richColors position='top-right' />
				{children}
			</body>
		</html>
	);
}
