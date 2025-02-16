import { MonthChart } from '../components/charts/MonthChart';

export default function DashboardPage() {
	return (
		<>
			<div className='flex flex-col justify-center items-center'>
				<div className='bg-gray-800 p-4 rounded-lg'>
					<MonthChart />
				</div>
			</div>
		</>
	);
}
