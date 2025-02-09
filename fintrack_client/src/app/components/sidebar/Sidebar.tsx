'use client';

import Link from 'next/link';
import { ChartIcon } from '../common/Icons/ChartIcon';
import { GastoIcon } from '../common/Icons/GastoIcon';
import { ListIcon } from '../common/Icons/ListIcon';
import { LogoutIcon } from '../common/Icons/LogoutIcon';
import { MenuIcon } from '../common/Icons/MenuIcon';
import { useState } from 'react';
import { logoutSession } from '@/app/services/auth.service';
import { useRouter } from 'next/navigation';

interface SidebarProps {}

export const Sidebar: React.FC<SidebarProps> = ({}) => {
	const [isOpen, setIsOpen] = useState(false);
	const router = useRouter();

	const toggleSidebar = () => {
		setIsOpen(!isOpen);
	};

	const logout = async () => {
		await logoutSession();
		router.push('/');
		setTimeout(() => {
			router.refresh();
		}, 100);
	};

	return (
		<>
			{/* Botón para abrir el menú en móviles */}
			<button
				onClick={toggleSidebar}
				aria-controls='default-sidebar'
				type='button'
				className='fixed top-4 left-4 z-50 p-2 text-gray-500 rounded-lg shadow-md'>
				<MenuIcon />
			</button>

			{/* Sidebar en pantallas grandes: siempre visible */}
			<aside
				id='default-sidebar'
				className={`bg-slate-900 h-screen p-4 sm:flex sm:flex-col sm:w-full ${
					isOpen
						? 'fixed inset-0 w-3/4 max-w-[250px] z-40 pt-16'
						: 'hidden sm:block'
				}`}>
				{/* Contenedor con scroll si el contenido es largo */}
				<div className='h-full overflow-y-auto'>
					<ul className='space-y-2 font-medium'>
						<li>
							<Link
								href={'/dashboard'}
								className='flex items-center p-2 rounded-lg opacity-60 hover:opacity-95 group hover:bg-slate-800'>
								<ChartIcon className='size-6' />
								<span className='ms-3'>Dashboard</span>
							</Link>
						</li>
						<li>
							<Link
								href={'/dashboard/create-gasto'}
								className='flex items-center p-2 rounded-lg group opacity-60 hover:opacity-95 hover:bg-slate-800'>
								<GastoIcon className='size-6' />
								<span className='flex-1 ms-3 whitespace-nowrap'>Gasto</span>
							</Link>
						</li>
						<li>
							<Link
								href={'/dashboard/lista-gastos'}
								className='flex items-center p-2 rounded-lg group opacity-60 hover:opacity-95 hover:bg-slate-800'>
								<ListIcon className='size-6' />
								<span className='flex-1 ms-3 whitespace-nowrap'>
									Lista de Gastos
								</span>
							</Link>
						</li>
						<li>
							<button
								onClick={logout}
								className='flex items-start p-2 rounded-lg group opacity-60 hover:opacity-95 hover:bg-slate-800 w-full'>
								<LogoutIcon className='size-6' />
								<span className='ms-3 whitespace-nowrap'>Sign In</span>
							</button>
						</li>
					</ul>
				</div>
			</aside>
		</>
	);
};
