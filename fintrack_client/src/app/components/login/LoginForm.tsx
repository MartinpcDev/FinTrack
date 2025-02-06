'use client';

import { login } from '@/app/services/auth.service';
import { AxiosError } from 'axios';
import { useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { toast } from 'sonner';

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
			<form onSubmit={onSubmit}>
				<input
					type='text'
					id='username'
					{...register('username', { required: 'El username es requerido' })}
				/>
				<input
					type='password'
					id='password'
					{...register('password', { required: 'La password es requerida' })}
				/>
				<button type='submit'>Submit</button>
			</form>
		</>
	);
};
