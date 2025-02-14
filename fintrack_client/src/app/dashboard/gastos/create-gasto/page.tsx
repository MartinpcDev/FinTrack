import { CreateGastoForm } from '@/app/components/gasto/CreateGastoForm';
import { allCategories } from '@/app/services/category.service';

export default async function CreateGastoPage() {
	const categoryData = await allCategories();
	return (
		<>
			<CreateGastoForm categories={categoryData} />
		</>
	);
}
