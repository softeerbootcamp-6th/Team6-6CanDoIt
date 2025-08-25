import SearchBar from '../../organisms/Common/SearchBar';
import { refactorMountainDataToOptions } from '../Main/utils.ts';
import useApiQuery from '../../../hooks/useApiQuery';
import type { MountainData } from '../../../types/mountainTypes';
import { useState } from 'react';
import { useSearchParams } from 'react-router-dom';

interface Option {
    id: number;
    name: string;
}

export default function AlertSearchSection() {
    const [searchParams, setSearchParams] = useSearchParams();
    const mountainId = Number(searchParams.get('mountainid'));

    const [selectedMountainId, setSelectedMountainId] =
        useState<number>(mountainId);

    const mouuntainChangeHandler = (id: number) => {
        setSelectedMountainId(id);
    };
    const submitHandler = (e: React.FormEvent) => {
        e.preventDefault();

        const next = new URLSearchParams(searchParams);
        next.set('mountainid', String(selectedMountainId));
        setSearchParams(next);
    };

    const { data: mountainsData } = useApiQuery<MountainData[]>(
        '/card/mountain',
        {},
        {
            retry: false,
        },
    );

    const mountainOptions: Option[] = refactorMountainDataToOptions(
        mountainsData ?? [],
    );

    return (
        <SearchBar
            searchBarTitle='어디 안전 정보를 확인해볼까요?'
            searchBarMessage='의 안전 정보'
            pageName='safety'
            mountainOptions={mountainOptions}
            selectedMountainId={selectedMountainId ?? 0}
            mountainChangeHandler={mouuntainChangeHandler}
            onSubmit={submitHandler}
        />
    );
}
