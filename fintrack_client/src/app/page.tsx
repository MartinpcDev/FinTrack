import { LoginForm } from './components/login/LoginForm';

export default async function HomePage() {
	return (
		<>
			<section className='section-center'>
				<div className='w-full rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0 border'>
					<div className='p-6 space-y-4 md:space-y-6 sm:p-8'>
						<LoginForm />
					</div>
				</div>
			</section>
		</>
	);
}
