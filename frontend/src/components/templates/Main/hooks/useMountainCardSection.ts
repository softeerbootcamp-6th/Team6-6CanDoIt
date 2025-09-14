import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { refactorMountainsData } from '../helper.ts';
import useMountainsData from '../../../../hooks/useMountainsData.ts';

export default function useMountainCardSection() {
    const navigate = useNavigate();
    const [hoveredCardId, setHoveredCardId] = useState<number | null>(null);

    const { data: mountainsData } = useMountainsData();

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
