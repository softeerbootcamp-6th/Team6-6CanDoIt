import type { Dispatch, SetStateAction } from 'react';
import type { SelectedMountainData } from '../../../types/mountainTypes';

export function createStartLoading({
    setSelectedMountain,
    setIsLoading,
}: {
    setSelectedMountain: Dispatch<SetStateAction<SelectedMountainData | null>>;
    setIsLoading: Dispatch<SetStateAction<boolean>>;
}) {
    return (mountainInfo: SelectedMountainData) => {
        setSelectedMountain(mountainInfo);
        setIsLoading(true);
        setTimeout(() => setIsLoading(false), 3000);
    };
}

export function createFormValidChange({
    setIsOpen,
}: {
    setIsOpen: Dispatch<SetStateAction<boolean>>;
}) {
    return (isValid: boolean) => {
        setIsOpen(!isValid);
    };
}
