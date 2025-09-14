import useApiQuery from './useApiQuery.ts';

export default function useFilterColumn() {
    return useApiQuery(
        '/card/interaction/keyword',
        {},
        {
            retry: false,
            networkMode: 'always',
            staleTime: 24 * 60 * 60 * 1000,
            gcTime: 24 * 60 * 60 * 1000,
            placeholderData: (prev) => prev,
        },
    );
}
