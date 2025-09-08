import { useRef, useState, useEffect } from 'react';

interface UseDraggableScrollParams {
    data: { dateTime?: string }[];
    scrollSelectedTime: string;
    onTimeSelect?: (time: string) => void;
    timeWindow: number;
    rawDataLength: number;
}

export function useDraggableScroll({
    data,
    scrollSelectedTime,
    onTimeSelect,
    timeWindow,
    rawDataLength,
}: UseDraggableScrollParams) {
    const scrollRef = useRef<HTMLDivElement>(null);
    const [isDragging, setIsDragging] = useState(false);
    const [startX, setStartX] = useState(0);
    const [scrollLeft, setScrollLeft] = useState(0);

    const startDrag = (x: number) => {
        setIsDragging(true);
        setStartX(x);
        setScrollLeft(scrollRef.current?.scrollLeft ?? 0);
    };

    const moveDrag = (x: number) => {
        if (!isDragging || !scrollRef.current) return;
        const walk = x - startX;
        scrollRef.current.scrollLeft = scrollLeft - walk;
    };

    const endDrag = () => {
        if (!scrollRef.current) return;
        setIsDragging(false);

        const container = scrollRef.current;
        const cellWidth = container.children[0]?.clientWidth ?? 1;
        const gap = 8;
        const scrollPos = container.scrollLeft;

        const nearestIndex = Math.round(scrollPos / (cellWidth + gap));

        const thresholdIndex = rawDataLength - timeWindow - 1;

        const finalIndex =
            thresholdIndex >= 0 && nearestIndex > thresholdIndex
                ? thresholdIndex
                : nearestIndex;

        container.scrollTo({
            left: finalIndex * (cellWidth + gap),
            behavior: 'smooth',
        });

        const selectedTime = data?.[finalIndex]?.dateTime;
        if (selectedTime && onTimeSelect) {
            onTimeSelect(selectedTime);
        }
    };

    useEffect(() => {
        if (!scrollRef.current || !data) return;

        const container = scrollRef.current;
        const gap = 8;
        const cellWidth = container.children[0]?.clientWidth ?? 1;

        const index = data.findIndex(
            (item) => item.dateTime === scrollSelectedTime,
        );
        if (index === -1) return;

        container.scrollTo({
            left: index * (cellWidth + gap),
            behavior: 'smooth',
        });
    }, [scrollSelectedTime, data]);

    return { scrollRef, startDrag, moveDrag, endDrag };
}
