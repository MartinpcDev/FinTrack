import { EditGastoForm } from '@/app/components/gasto/EditGastoForm';
import { allCategories } from '@/app/services/category.service';
import { getTransaction } from '@/app/services/transaction.service';

type pageProps = {
	params: Promise<{ gastoId: string }>;
};

export default async function EditTransactionPage({ params }: pageProps) {
	const id = parseInt((await params).gastoId);
	const transaction = await getTransaction(id);
	const categories = await allCategories();
	return (
		<>
			{transaction && (
				<EditGastoForm
					id={id}
					transaction={transaction}
					categories={categories}
				/>
			)}
		</>
	);
}
