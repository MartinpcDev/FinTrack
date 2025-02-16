'use client';

import { Transaction } from '@/app/models/transaction.model';
import { getAllByMonthAndYear } from '@/app/services/transaction.service';
import { useEffect, useState } from 'react';
import {
	Bar,
	BarChart,
	Cell,
	Legend,
	Pie,
	PieChart,
	ResponsiveContainer,
	Tooltip,
	XAxis,
	YAxis
} from 'recharts';
import { toast } from 'sonner';

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

interface CategoryData {
	[categoryName: string]: { name: string; value: number };
}

interface CustomizedLabelProps {
	cx: number;
	cy: number;
	midAngle: number;
	innerRadius: number;
	outerRadius: number;
	percent: number;
	index: number;
}

export const MonthChart: React.FC = ({}) => {
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

	const RADIAN = Math.PI / 180;

	const renderCustomizedLabel = ({
		cx,
		cy,
		midAngle,
		innerRadius,
		outerRadius,
		percent,
		index
	}: CustomizedLabelProps) => {
		const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
		const x = cx + radius * Math.cos(-midAngle * RADIAN);
		const y = cy + radius * Math.sin(-midAngle * RADIAN);

		return (
			<text
				x={x}
				y={y}
				fill='white'
				textAnchor={x > cx ? 'start' : 'end'}
				dominantBaseline='central'>
				{`${pieData[index].name} ${(percent * 100).toFixed(0)}%`}
			</text>
		);
	};
	return (
		<>
			<ResponsiveContainer width={400} height={400}>
				<PieChart>
					<Pie
						data={pieData}
						cx='50%'
						cy='50%'
						labelLine={false}
						label={renderCustomizedLabel}
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
			</ResponsiveContainer>
		</>
	);
};
