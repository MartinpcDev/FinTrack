import { Toaster } from 'sonner';
import { montserrat } from '../utils/fonts';
import { Sidebar } from '../components/sidebar/Sidebar';

export default function DashboardLayout({
	children
}: Readonly<{
	children: React.ReactNode;
}>) {
	return (
		<html lang='es'>
			<body className={`${montserrat.className} antialiased`}>
				<Toaster position='top-right' richColors />
				<div className='grid grid-cols-[20%_1fr] grid-rows-[1fr] gap-y-[10px] gap-x-[10px] h-screen'>
					<Sidebar />
					{children}
				</div>
			</body>
		</html>
	);
}
