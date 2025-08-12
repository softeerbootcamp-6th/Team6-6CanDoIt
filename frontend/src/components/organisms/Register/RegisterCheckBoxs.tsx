import { css } from '@emotion/react';
import CheckBox from '../../atoms/Form/ChekBox';
import type { ColorValueType } from '../../../types/themeTypes';

export default function RegisterCheckBoxs() {
    return (
        <>
            <CheckBox
                id='all'
                text='모두 동의합니다.'
                subTitle='종성짱짱맨'
                grey={98 as ColorValueType}
            />
            <div css={checkBoxStyles}>
                <CheckBox id='a1-button' text='[필수] 만 14세 이상입니다' />
                <CheckBox id='a2-button' text='[필수] 이용약관 동의' />
                <CheckBox
                    id='a3-button'
                    text='[선택] 개인정보 마케팅 활용 동의'
                />
                <CheckBox
                    id='a4-button'
                    text='[선택] 이벤트, 알림 및 SMS 등 수신'
                />
            </div>
        </>
    );
}

const checkBoxStyles = css`
    display: flex;
    flex-direction: column;
    gap: 0.4rem;
`;
