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
