interface ChartIconProps {
	className?: string;
}

export const ChartIcon: React.FC<ChartIconProps> = ({ className }) => {
	return (
		<>
			<svg
				xmlns='http://www.w3.org/2000/svg'
				width='24'
				height='24'
				viewBox='0 0 24 24'
				fill='currentColor'
				className={`${className} icon icon-tabler icons-tabler-filled icon-tabler-chart-pie`}>
				<path stroke='none' d='M0 0h24v24H0z' fill='none' />
				<path d='M9.883 2.207a1.9 1.9 0 0 1 2.087 1.522l.025 .167l.005 .104v7a1 1 0 0 0 .883 .993l.117 .007h6.8a2 2 0 0 1 2 2a1 1 0 0 1 -.026 .226a10 10 0 1 1 -12.27 -11.933l.27 -.067l.11 -.02z' />
				<path d='M14 3.5v5.5a1 1 0 0 0 1 1h5.5a1 1 0 0 0 .943 -1.332a10 10 0 0 0 -6.11 -6.111a1 1 0 0 0 -1.333 .943z' />
			</svg>
		</>
	);
};
