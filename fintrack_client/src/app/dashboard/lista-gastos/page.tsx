import { TableGasto } from '@/app/components/gasto/TableGasto';
import { getAllTransactions } from '@/app/services/transaction.service';

export default async function ListaGastosPage() {
	return (
		<>
			<TableGasto />
		</>
	);
}
