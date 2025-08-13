import { css } from '@emotion/react';
import CheckBox from '../../atoms/Form/CheckBox.tsx';
import type { ColorValueType } from '../../../types/themeTypes';

export default function RegisterCheckBoxes() {
    return (
        <>
            <CheckBox {...allCheckBoxProps} />
            <div css={checkBoxStyles}>
                {checkBoxes.map(({ id, text }) => (
                    <CheckBox key={id} id={id} text={text} />
                ))}
            </div>
        </>
    );
}

const allCheckBoxProps = {
    id: 'all',
    text: '모두 동의합니다.',
    subTitle: '종성짱짱맨',
    grey: 98 as ColorValueType,
};

const checkBoxes = [
    { id: 'a1-button', text: '[필수] 만 14세 이상입니다' },
    { id: 'a2-button', text: '[필수] 이용약관 동의' },
    { id: 'a3-button', text: '[선택] 개인정보 마케팅 활용 동의' },
    { id: 'a4-button', text: ' [선택] 이벤트, 알림 및 SMS 등 수신' },
];

const checkBoxStyles = css`
    display: flex;
    flex-direction: column;
    gap: 0.4rem;
`;
