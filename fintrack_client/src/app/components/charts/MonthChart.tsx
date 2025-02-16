'use client';

import { Transaction } from '@/app/models/transaction.model';
import { getAllByMonthAndYear } from '@/app/services/transaction.service';
import { useEffect, useState } from 'react';
import { Cell, Legend, Pie, PieChart } from 'recharts';
import { toast } from 'sonner';
import { CategoryData, renderCustomizedLabel } from './RenderCustomLabel';
import { formatDate } from '../../utils/dateFormat';
import { GastoTotalBox } from './GastoTotalBox';

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

export const MonthChart: React.FC = () => {
	const [data, setData] = useState<Transaction[]>([]);
	const [month, setMonth] = useState(2);
	const [year, setYear] = useState(new Date().getFullYear());

	useEffect(() => {
		async function getData() {
			try {
				const response = await getAllByMonthAndYear(month, year);
				setData(response!);
			} catch (error: any) {
				toast.error(error.message);
			}
		}

		getData();
	}, [month, year]);

	const categoryData = data.reduce((acc: CategoryData, item) => {
		const categoryName = item.category.name;
		if (!acc[categoryName]) {
			acc[categoryName] = { name: categoryName, value: 0 };
		}
		acc[categoryName].value += item.amount;
		return acc;
	}, {});

	const pieData = Object.values(categoryData);

	const handlePreviousYear = () => {
		setYear(prev => prev - 1);
	};

	const handleNextYear = () => {
		setYear(prev => prev + 1);
	};

	const handlePreviousMonth = () => {
		if (month > 1) {
			setMonth(prev => prev - 1);
		}
	};

	const handleNextMonth = () => {
		if (month < 12) {
			setMonth(prev => prev + 1);
		}
	};

	return (
		<>
			<GastoTotalBox month={month} year={year} />
			<div className='flex flex-row items-center space-x-5'>
				<div className='flex flex-row items-center space-x-3'>
					<button onClick={handlePreviousYear}>-</button>
					<span className='text-sky-400'>{year}</span>
					<button onClick={handleNextYear}>+</button>
				</div>
				<div className='flex flex-row items-center space-x-3'>
					<button onClick={handlePreviousMonth}>-</button>
					<span className='text-sky-400'>
						{formatDate(new Date(`${month}/1/${year}`), 'es', {
							month: 'long'
						})}
					</span>
					<button onClick={handleNextMonth}>+</button>
				</div>
			</div>
			{data.length > 0 ? (
				<div className='flex flex-col justify-center items-center'>
					<PieChart width={400} height={400}>
						<Pie
							data={pieData}
							cx='50%'
							cy='50%'
							labelLine={false}
							label={props => renderCustomizedLabel({ ...props, categoryData })}
							outerRadius={80}
							fill='#8884d8'
							dataKey='value'>
							{pieData.map((entry, index) => (
								<Cell
									key={`cell-${index}`}
									fill={COLORS[index % COLORS.length]}
								/>
							))}
						</Pie>
						<Legend />
					</PieChart>
				</div>
			) : (
				<div className='flex flex-col justify-center items-center h-full w-full'>
					<p>No hay Datos</p>
				</div>
			)}
		</>
	);
};
