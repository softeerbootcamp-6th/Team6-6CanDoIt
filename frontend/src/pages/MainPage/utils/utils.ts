import type { Dispatch, SetStateAction } from 'react';

export function formValidChange({
    setIsOpen,
}: {
    setIsOpen: Dispatch<SetStateAction<boolean>>;
}) {
    return (isValid: boolean) => {
        setIsOpen(!isValid);
    };
}
