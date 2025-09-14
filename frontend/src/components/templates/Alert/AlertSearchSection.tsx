import { css } from '@emotion/react';

import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';

import useApiQuery from '../../../hooks/useApiQuery';

import { refactorMountainDataToOptions } from '../Main/utils.ts';
import type { MountainData } from '../../../types/mountainTypes';
import type { Option } from '../../../types/searchBarTypes';

import Modal from '../../molecules/Modal/RegisterModal.tsx';
import SearchBar from '../../organisms/Common/SearchBar';

export default function AlertSearchSection() {
    const [searchParams, setSearchParams] = useSearchParams();
    const [errorMessage, setErrorMessage] = useState<string>('');
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

    const { data: mountainsData, isError: isMountainsError } = useApiQuery<
        MountainData[]
    >(
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
    useEffect(() => {
        if (isMountainsError) {
            setErrorMessage('산 정보를 불러오는데 실패했습니다.');
        }
    }, [isMountainsError]);

    const mountainOptions: Option[] = refactorMountainDataToOptions(
        mountainsData ?? [],
    );

    return (
        <div
            css={css`
                margin-top: 1rem;
            `}
        >
            <SearchBar
                searchBarTitle='어디 안전 정보를 확인해볼까요?'
                searchBarMessage='의 안전 정보'
                pageName='safety'
                mountainOptions={mountainOptions}
                selectedMountainId={selectedMountainId ?? 0}
                mountainChangeHandler={mouuntainChangeHandler}
                onSubmit={submitHandler}
            />
            {errorMessage && (
                <Modal onClose={() => setErrorMessage('')}>
                    {errorMessage}
                </Modal>
            )}
        </div>
    );
}
