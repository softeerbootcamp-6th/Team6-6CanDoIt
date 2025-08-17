import { css } from '@emotion/react';
import CheckBox from '../../atoms/Form/CheckBox.tsx';
import type { ColorValueType } from '../../../types/themeTypes';
import { useState } from 'react';
import { theme } from '../../../theme/theme.ts';

interface CheckBoxItem {
    id: string;
    text: string;
    required: boolean;
    Modal?: React.ReactNode;
}

export default function RegisterCheckBoxes() {
    const initialCheckedItems: Record<string, boolean> = {};
    checkBoxes.forEach((box) => {
        initialCheckedItems[box.id] = false;
    });

    const [checkedItems, setCheckedItems] = useState(initialCheckedItems);
    const [allChecked, setAllChecked] = useState(false);
    const [openModalId, setOpenModalId] = useState<string | null>(null);

    const handleAllCheck = () => {
        const newState = !allChecked;
        const updatedItems: Record<string, boolean> = {};
        checkBoxes.forEach((box) => {
            updatedItems[box.id] = newState;
        });

        setCheckedItems(updatedItems);
        setAllChecked(newState);
    };

    const handleIndividualCheck = (id: string) => {
        const updatedItems = { ...checkedItems, [id]: !checkedItems[id] };
        const allAreChecked = checkBoxes.every((box) => updatedItems[box.id]);

        setCheckedItems(updatedItems);
        setAllChecked(allAreChecked);
    };

    const handleModalToggle = (id: string) => {
        setOpenModalId((prev) => (prev === id ? null : id));
    };

    return (
        <>
            <CheckBox
                {...allCheckBoxProps}
                onChange={handleAllCheck}
                checked={allChecked}
            />
            <div css={checkBoxStyles}>
                {checkBoxes.map((box) => (
                    <div css={checkLineWrapper}>
                        <CheckBox
                            key={box.id}
                            id={box.id}
                            text={box.text}
                            onChange={() => handleIndividualCheck(box.id)}
                            checked={checkedItems[box.id]}
                        />
                        {box.Modal && (
                            <button
                                type='button'
                                css={buttonStyles}
                                onClick={() => handleModalToggle(box.id)}
                            >
                                내용 보기
                            </button>
                        )}
                        {openModalId === box.id && box.Modal}
                    </div>
                ))}
            </div>
        </>
    );
}

const allCheckBoxProps = {
    id: 'all-check-box',
    text: '모두 동의합니다.',
    subTitle: '종성짱짱맨',
    grey: 98 as ColorValueType,
    required: false,
};

const checkBoxes: CheckBoxItem[] = [
    { id: 'age', text: '[필수] 만 14세 이상입니다', required: true },
    {
        id: 'terms',
        text: '[필수] 이용약관 동의',
        required: true,
        Modal: <div>check</div>,
    },
    {
        id: 'privacy',
        text: '[선택] 개인정보 마케팅 활용 동의',
        required: false,
        Modal: '[내용보기]',
    },
    {
        id: 'event',
        text: '[선택] 이벤트, 알림 및 SMS 등 수신',
        required: false,
    },
];

const { colors } = theme;

const checkBoxStyles = css`
    display: flex;
    flex-direction: column;
    gap: 0.4rem;
`;

const checkLineWrapper = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
`;

const buttonStyles = css`
    all: unset;
    color: ${colors.grey[70]};
    font-size: 0.75rem;
    cursor: pointer;
    border-bottom: 1px solid ${colors.grey[70]};
`;
