'use client';

import { Category } from '@/app/models/category.model';
import {
	Transaction,
	TransactionRequest
} from '@/app/models/transaction.model';
import { updateTransaction } from '@/app/services/transaction.service';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'sonner';
import { ErrorMessage } from '../common/ErrorMessage';

interface EditGastoFormProps {
	id: number;
	transaction: Transaction;
	categories: Category[];
}

export const EditGastoForm: React.FC<EditGastoFormProps> = ({
	id,
	transaction,
	categories
}) => {
	const {
		register,
		handleSubmit,
		formState: { errors }
	} = useForm<TransactionRequest>();
	const router = useRouter();

	const onSubmit = handleSubmit(async data => {
		try {
			const response = await updateTransaction(id, data);
			toast.success(response?.message);
			router.push('/dashboard/lista-gastos');
		} catch (error: any) {
			toast.error(error.message);
		}
	});

	return (
		<>
			<div className='flex flex-col justify-center items-center space-y-4'>
				<div className='text-lg uppercase font-bold'>
					<h1>Editar Gasto o Ingreso</h1>
				</div>
				<form
					onSubmit={onSubmit}
					className='w-10/12 space-y-3 md:w-1/3 min-w-[50%] transition-all duration-150'>
					<div className='relative'>
						<select
							id='type'
							defaultValue={transaction.type}
							className='py-3 px-4 pe-9 block w-full bg-neutral-900 border-neutral-700 rounded-lg text-neutral-400 focus:border-neutral-500 focus:ring-neutral-600 disabled:opacity-50 disabled:pointer-events-none placeholder-neutral-500'
							{...register('type', { required: 'El tipo es requerido' })}>
							<option disabled value=''>
								Seleccione el Tipo
							</option>
							<option value='INGRESO'>Ingreso</option>
							<option value='GASTO'>Gasto</option>
						</select>
						{errors.type?.message && (
							<ErrorMessage message={errors.type.message} />
						)}
					</div>
					<div className='relative'>
						<input
							type='number'
							id='amount'
							step='0.01'
							defaultValue={transaction.amount}
							className='peer p-4 block w-full bg-neutral-900 border-neutral-700 rounded-lg text-sm placeholder:text-transparent text-neutral-400 focus:border-neutral-500 focus:ring-neutral-600 disabled:opacity-50 disabled:pointer-events-none [&::-webkit-inner-spin-button]:appearance-none [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:m-0 [&]:-moz-appearance-textfield 
                  focus:pt-6
          focus:pb-2
          [&:not(:placeholder-shown)]:pt-6
          [&:not(:placeholder-shown)]:pb-2
          autofill:pt-6
          autofill:pb-2'
							placeholder='Ingresar el amount'
							{...register('amount', { required: 'El amount es requerido' })}
						/>
						<label
							htmlFor='amount'
							className='absolute top-0 start-0 p-4 h-full text-sm truncate pointer-events-none transition ease-in-out duration-100 border border-transparent  origin-[0_0] dark:text-white peer-disabled:opacity-50 peer-disabled:pointer-events-none
                  peer-focus:scale-90
                  peer-focus:translate-x-0.5
                  peer-focus:-translate-y-1.5
                  peer-focus:text-gray-500 dark:peer-focus:text-neutral-500
            peer-[:not(:placeholder-shown)]:scale-90
            peer-[:not(:placeholder-shown)]:translate-x-0.5
            peer-[:not(:placeholder-shown)]:-translate-y-1.5
            peer-[:not(:placeholder-shown)]:text-gray-500 dark:peer-[:not(:placeholder-shown)]:text-neutral-500 text-neutral-500'>
							Cantidad
						</label>
						{errors.amount?.message && (
							<ErrorMessage message={errors.amount.message} />
						)}
					</div>
					<div className='relative'>
						<select
							id='category_id'
							defaultValue={transaction.category.id}
							className='py-3 px-4 pe-9 block w-full bg-neutral-900 border-neutral-700 rounded-lg text-neutral-400 focus:border-blue-500 focus:ring-neutral-600 disabled:opacity-50 disabled:pointer-events-none placeholder-neutral-500'
							{...register('category_id', {
								required: 'La categoria es requerida'
							})}>
							<option disabled value=''>
								Seleccione la categoria
							</option>
							{categories.map(category => (
								<option key={category.id} value={category.id}>
									{category.name}
								</option>
							))}
						</select>
						{errors.category_id?.message && (
							<ErrorMessage message={errors.category_id.message} />
						)}
					</div>
					<div className='flex flex-col justify-center items-center'>
						<button
							type='submit'
							className='bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm sm:w-auto px-5 py-2.5 text-center transition-colors w-1/2'>
							Editar
						</button>
					</div>
				</form>
			</div>
		</>
	);
};
