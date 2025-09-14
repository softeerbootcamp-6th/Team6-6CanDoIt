import useApiQuery from './useApiQuery.ts';
import type { MountainCourse } from '../types/mountainTypes';

export default function useCoursesData(selectedMountainId: number) {
    return useApiQuery<MountainCourse[]>(
        `/card/mountain/${selectedMountainId}/course`,
        {},
        {
            enabled: selectedMountainId !== 0,
            retry: false,
            networkMode: 'always',
            staleTime: 24 * 60 * 60 * 1000,
            gcTime: 24 * 60 * 60 * 1000,
        },
    );
}
