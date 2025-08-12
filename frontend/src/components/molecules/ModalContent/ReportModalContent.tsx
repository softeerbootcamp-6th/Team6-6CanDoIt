import LabelButtonsColumn from '../LabelButtonsColumn/LabelButtonsColumn';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import ModalTextInputBox from '../ModalTextInputBox/ModalTextInputBox';
import ImageInputField from '../ImageInputField/ImageInputField.tsx';

interface FilterColumn {
    title: string;
    filterLabels: string[];
}
interface Props {
    filterColumns: FilterColumn[];
}

export default function ReportModalContent({ filterColumns }: Props) {
    return (
        <div css={reportModalContentStyle}>
            <ImageInputField />
            <div css={rightPaneStyle}>
                <ModalTextInputBox placeholder='사진과 함께 올릴 설명을 100글자 이내로 작성해주세요.' />
                <div css={labelButtonsRowStyle}>
                    {filterColumns?.map((column) => (
                        <LabelButtonsColumn
                            title={column.title}
                            filterLabels={column.filterLabels}
                        />
                    ))}
                </div>
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
