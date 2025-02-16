'use client';

import { getExpenseByMonthAndYear } from '@/app/services/transaction.service';
import { useEffect, useState } from 'react';
import { toast } from 'sonner';

interface GastoTotalBoxProps {
	month: number;
	year: number;
}

export const GastoTotalBox: React.FC<GastoTotalBoxProps> = ({
	month,
	year
}) => {
	const [gasto, setGasto] = useState<number>(0);
	useEffect(() => {
		async function getGasto() {
			try {
				const response = await getExpenseByMonthAndYear(month, year);
				setGasto(response!);
			} catch (error: any) {
				toast.error(error.message);
			}
		}

		getGasto();
	}, [month, year]);

	return (
		<>
			<div className='flex flex-row justify-center items-center text-lg pb-4'>
				Gasto Total del Mes:
				<span className='px-3 text-yellow-600 font-bold'>
					{gasto.toFixed(2)}
				</span>
			</div>
		</>
	);
};
