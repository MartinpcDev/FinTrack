interface RenderCustomLabelProps {
	categoryData: CategoryData;
	cx: number;
	cy: number;
	midAngle: number;
	innerRadius: number;
	outerRadius: number;
	percent: number;
	index: number;
}

export interface CategoryData {
	[categoryName: string]: { name: string; value: number };
}

export const renderCustomizedLabel = ({
	categoryData = {},
	cx,
	cy,
	midAngle,
	innerRadius,
	outerRadius,
	percent,
	index
}: RenderCustomLabelProps) => {
	const RADIAN = Math.PI / 180;
	const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
	const x = cx + radius * Math.cos(-midAngle * RADIAN);
	const y = cy + radius * Math.sin(-midAngle * RADIAN);
	const pieData = Object.values(categoryData);

	if (!pieData[index]) return null;

	return (
		<>
			<text
				x={x}
				y={y}
				fill='white'
				textAnchor={x > cx ? 'start' : 'end'}
				dominantBaseline='central'>
				{`${pieData[index].name} ${(percent * 100).toFixed(0)}%`}
			</text>
		</>
	);
};
