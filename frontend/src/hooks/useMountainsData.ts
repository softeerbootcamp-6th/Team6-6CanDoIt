import useApiQuery from './useApiQuery.ts';
import type { MountainData } from '../types/mountainTypes';

export default function useMountainsData() {
    return useApiQuery<MountainData[]>(
        '/card/mountain',
        {},
        {
            retry: false,
            networkMode: 'always',
            staleTime: 5 * 60 * 1000,
            gcTime: 24 * 60 * 60 * 1000,
            placeholderData: (prev) => prev,
        },
    );
}
