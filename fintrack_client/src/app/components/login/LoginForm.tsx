'use client';

import { login } from '@/app/services/auth.service';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'sonner';
import { ErrorMessage } from '../common/ErrorMessage';

interface LoginFormProps {}

export const LoginForm: React.FC<LoginFormProps> = ({}) => {
	const {
		register,
		handleSubmit,
		formState: { errors }
	} = useForm<LoginRequest>();
	const router = useRouter();

	const onSubmit = handleSubmit(async data => {
		try {
			const response = await login(data);
			toast.success(response?.message);
			router.push('/dashboard');
		} catch (error: any) {
			if (error.details) {
				error.details.map((details: string) => {
					toast.error(details);
				});
			} else {
				toast.error(error.message);
			}
		}
	});

	return (
		<>
			<form onSubmit={onSubmit} className='max-w-sm mx-auto'>
				<div className='mb-5'>
					<label htmlFor='username' className='block mb-2 text-sm font-medium'>
						Username
					</label>
					<input
						type='text'
						id='username'
						className='bg-gray-50 border border-gray-300 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 text-gray-900'
						placeholder='Ingrese el username'
						{...register('username', { required: 'El username es requerido' })}
					/>
					{errors.username?.message && (
						<ErrorMessage message={errors.username.message} />
					)}
				</div>
				<div className='mb-5'>
					<label htmlFor='password' className='block mb-2 text-sm font-medium'>
						Password
					</label>
					<input
						type='password'
						id='password'
						className='bg-gray-50 border border-gray-300 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 text-gray-900'
						placeholder='Ingrese el password'
						{...register('password', { required: 'La password es requerida' })}
					/>
					{errors.password?.message && (
						<ErrorMessage message={errors.password.message} />
					)}
				</div>
				<button
					type='submit'
					className='bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors'>
					Ingresar
				</button>
			</form>
		</>
	);
};
