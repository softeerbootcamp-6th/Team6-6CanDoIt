import LabelButtonsColumn from '../../organisms/Report/LabelButtonsColumn.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import ModalTextInputBox from '../Input/ModalTextInputBox.tsx';
import ImageInputField from '../Input/ImageInputField.tsx';
import { useState } from 'react';
import type {
    FilterColumn,
    Keyword,
    SelectedColumn,
} from '../../../types/FilterTypes';

interface PropsState {
    filterColumn: FilterColumn;
}

export default function ReportModalContent({ filterColumn }: PropsState) {
    const [selectedKeywords, setSelectedKeywords] = useState<SelectedColumn>({
        weatherKeywords: [],
        rainKeywords: [],
        etceteraKeywords: [],
    });

    const updateSelectedKeywords = (
        keyword: Keyword,
        selectedIds: number[],
    ) => {
        setSelectedKeywords({
            ...selectedKeywords,
            [keyword]: selectedIds,
        });
    };

    return (
        <div css={reportModalContentStyle}>
            <ImageInputField />
            <div css={rightPaneStyle}>
                <ModalTextInputBox placeholder='사진과 함께 올릴 설명을 100글자 이내로 작성해주세요.' />
                <div css={labelButtonsRowStyle}>
                    {(Object.keys(filterColumn) as Keyword[]).map((keyword) => (
                        <LabelButtonsColumn
                            key={keyword}
                            keyword={keyword}
                            filterLabels={filterColumn[keyword]}
                            selectedIds={selectedKeywords[keyword]}
                            onSelectionChange={(selectedIds) =>
                                updateSelectedKeywords(keyword, selectedIds)
                            }
                        />
                    ))}
                </div>

                {(Object.keys(filterColumn) as Keyword[]).map((keyword) =>
                    selectedKeywords[keyword].map((id) => (
                        <input
                            key={`${keyword}-${id}`}
                            type='hidden'
                            name={`${keyword}`}
                            value={id}
                        />
                    )),
                )}
            </div>
        </div>
    );
}

const { colors } = theme;

const reportModalContentStyle = css`
    width: 100%;
    padding: 1rem 1.5rem 2rem;
    box-sizing: border-box;
    display: flex;
    gap: 1.5rem;
    border-bottom: 1px solid ${colors.grey[50]};
`;

const rightPaneStyle = css`
    display: flex;
    flex-direction: column;
`;

const labelButtonsRowStyle = css`
    display: flex;
    gap: 1.5rem;
    margin-top: 2.5rem;
`;
