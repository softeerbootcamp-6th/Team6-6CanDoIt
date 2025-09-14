import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import useApiQuery from '../../../../hooks/useApiQuery.ts';
import type { MountainData } from '../../../../types/mountainTypes';
import { refactorMountainsData } from '../utils.ts';

export default function useMountainCardSection() {
    const navigate = useNavigate();
    const [hoveredCardId, setHoveredCardId] = useState<number | null>(null);

    const { data: mountainsData } = useApiQuery<MountainData[]>(
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

    const cardClickHandler = (mountainId: number) => {
        navigate(`/forecast?mountainid=${mountainId}`);
    };

    const data = refactorMountainsData(mountainsData ?? []);

    return {
        data,
        cardClickHandler,
        hoveredCardId,
        setHoveredCardId,
    };
}
