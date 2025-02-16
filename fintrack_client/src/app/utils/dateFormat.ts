export const formatDate = (
	date: Date,
	locale: Intl.LocalesArgument,
	options?: Intl.DateTimeFormatOptions
) => {
	return Intl.DateTimeFormat(locale, options).format(date);
};
