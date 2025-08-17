import { type Dispatch, type SetStateAction, useState } from 'react';
import MainSearchSection from '../../components/templates/Main/MainSearchSection.tsx';
import MountainCardSection from '../../components/templates/Main/MountainCardSection.tsx';
import Loading from '../../components/organisms/Loading/Loading.tsx';
import { css } from '@emotion/react';
import type {
    MountainData,
    SelectedMountainData,
} from '../../types/mountainTypes';
import { createFormValidChange, createStartLoading } from './utils/utils.ts';

export default function MainPage() {
    const [isOpen, setIsOpen] = useState(false);
    const [mountainsData, setMountainsData] = useState<MountainData[]>([]);
    const [selectedMountain, setSelectedMountain] =
        useState<SelectedMountainData | null>(null);
    const [isLoading, setIsLoading] = useState(false);

    const handleStartLoading = createStartLoading({
        setSelectedMountain,
        setIsLoading,
    });
    const handleFormValidChange = createFormValidChange({ setIsOpen });

    const mainSearchSectionProps = createMainSearchSectionProps({
        mountainsData,
        onSearchClick: handleStartLoading,
        onFormValidChange: handleFormValidChange,
    });
    const mountainCardSectionProps = createMountainCardSectionProps({
        mountainsData,
        setMountainsData,
        onCardClick: handleStartLoading,
    });

    if (isLoading) {
        return <Loading {...loadingProps(selectedMountain!)} />;
    }

    return (
        <>
            {isOpen && <div css={backgroundStyle} />}
            <div css={overBackgroundStyle}>
                <MainSearchSection {...mainSearchSectionProps} />
            </div>
            <MountainCardSection {...mountainCardSectionProps} />
        </>
    );
}

const createMountainCardSectionProps = ({
    mountainsData,
    setMountainsData,
    onCardClick,
}: {
    mountainsData: MountainData[];
    setMountainsData: Dispatch<SetStateAction<MountainData[]>>;
    onCardClick: (data: SelectedMountainData) => void;
}) => ({
    mountainsData,
    setMountainsData,
    onCardClick,
});

const createMainSearchSectionProps = ({
    mountainsData,
    onSearchClick,
    onFormValidChange,
}: {
    mountainsData: MountainData[];
    onSearchClick: (data: SelectedMountainData) => void;
    onFormValidChange: (isValid: boolean) => void;
}) => ({
    mountainsData,
    onSearchClick,
    onFormValidChange,
});

const loadingProps = (selectedMountain: SelectedMountainData) => ({
    mountainTitle: selectedMountain.mountainName,
    mountainDescription: selectedMountain.mountainDescription,
});

const backgroundStyle = css`
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0.7;
    background: linear-gradient(180deg, #000 0%, rgba(0, 0, 0, 0) 100%);
`;

const overBackgroundStyle = css`
    position: relative;
    z-index: 10;

    display: flex;
    flex-direction: column;
    gap: 2rem;

    margin-bottom: 2rem;
`;
