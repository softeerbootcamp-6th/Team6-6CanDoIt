import { css } from '@emotion/react';
import CheckBox from '../../atoms/Form/CheckBox.tsx';
import type { ColorValueType } from '../../../types/themeTypes';
import { useState } from 'react';

interface CheckBoxItem {
    id: string;
    text: string;
    required: boolean;
    ModalText?: string;
}

export default function RegisterCheckBoxes() {
    const initialCheckedItems: Record<string, boolean> = {};
    checkBoxes.forEach((box) => {
        initialCheckedItems[box.id] = false;
    });

    const [checkedItems, setCheckedItems] = useState(initialCheckedItems);
    const [allChecked, setAllChecked] = useState(false);

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

    return (
        <>
            <CheckBox
                {...allCheckBoxProps}
                onChange={handleAllCheck}
                checked={allChecked}
            />
            <div css={checkBoxStyles}>
                {checkBoxes.map((box) => (
                    <CheckBox
                        key={box.id}
                        id={box.id}
                        text={box.text}
                        onChange={() => handleIndividualCheck(box.id)}
                        checked={checkedItems[box.id]}
                    />
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
        ModalText: '가나다라마바사',
    },
    {
        id: 'privacy',
        text: '[선택] 개인정보 마케팅 활용 동의',
        required: false,
        ModalText: '가나다라마바사',
    },
    {
        id: 'event',
        text: '[선택] 이벤트, 알림 및 SMS 등 수신',
        required: false,
    },
];

const checkBoxStyles = css`
    display: flex;
    flex-direction: column;
    gap: 0.4rem;
`;
