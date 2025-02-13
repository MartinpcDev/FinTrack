'use client';

import { Transaction } from '@/app/models/transaction.model';
import {
	deleteTransaction,
	getAllTransactions
} from '@/app/services/transaction.service';
import Link from 'next/link';
import { useEffect, useState } from 'react';
import { toast } from 'sonner';

export const TableGasto: React.FC = () => {
	const [data, setData] = useState<Transaction[]>([]);
	const [page, setPage] = useState<number>(0);
	const [total, setTotal] = useState<number>(1);
	const [size, setSize] = useState<number>(10);
	const [totalPages, setTotalPages] = useState<number>(0);

	useEffect(() => {
		async function getData() {
			try {
				const response = await getAllTransactions(page, size);
				setData(response!.content);
				setPage(response!.page);
				setSize(response!.size);
				setTotal(response!.total);
				setTotalPages(response!.total_pages);
			} catch (error: any) {
				toast.error(error.message);
			}
		}
		getData();
	}, [page, size, total]);

	const handleNextPage = () => {
		if (page < totalPages - 1) {
			setPage(prevPage => prevPage + 1);
		}
	};

	const handlePreviousPage = () => {
		if (page > 0) {
			setPage(prevPage => prevPage - 1);
		}
	};

	const deleteAction = async (id: number) => {
		try {
			const response = await deleteTransaction(id);
			setData(prevState => prevState.filter(t => t.id !== id));
			toast.success(response?.message);
		} catch (error: any) {
			toast.error(error.message);
		}
	};

	return (
		<>
			<div className='relative overflow-x-auto flex flex-col justify-center items-center px-4'>
				<table className='w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400'>
					<thead className='text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400'>
						<tr>
							<th scope='col' className='px-6 py-3'>
								Tipo
							</th>
							<th scope='col' className='px-6 py-3'>
								Cantidad
							</th>
							<th scope='col' className='px-6 py-3'>
								Categoria
							</th>
							<th scope='col' className='px-6 py-3'></th>
							<th scope='col' className='px-6 py-3'></th>
						</tr>
					</thead>
					<tbody>
						{data.map(transaction => (
							<tr
								key={transaction.id}
								className='bg-white border-b dark:bg-gray-800 dark:border-gray-700'>
								<th
									scope='row'
									className='px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white'>
									{transaction.type}
								</th>
								<td className='px-6 py-4'>{transaction.amount}</td>
								<td className='px-6 py-4'>{transaction.category.name}</td>
								<td className='px-6 py-4'>
									<Link
										href={`/dashboard/products/${encodeURIComponent(
											transaction.id
										)}`}
										className='font-medium text-yellow-400 hover:underline'>
										Editar
									</Link>
								</td>
								<td className='px-6 py-4'>
									<button
										onClick={() => deleteAction(transaction.id)}
										className='font-medium text-red-400 hover:underline'>
										Eliminar
									</button>
								</td>
							</tr>
						))}
					</tbody>
				</table>
				<div className='flex justify-center items-center my-5'>
					<button
						onClick={handlePreviousPage}
						disabled={page === 0}
						className='flex items-center justify-center px-3 h-8 text-sm font-medium border  rounded-lg bg-gray-800 border-gray-700 text-gray-400 hover:bg-gray-700 hover:text-white'>
						Previous
					</button>
					<button
						onClick={handleNextPage}
						disabled={page === totalPages}
						className='flex items-center justify-center px-3 h-8 ms-3 text-sm font-medium border rounded-lg bg-gray-800 border-gray-700 text-gray-400 hover:bg-gray-700 hover:text-white'>
						Next
					</button>
				</div>
			</div>
		</>
	);
};
