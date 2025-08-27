import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

import { useNavigate } from 'react-router-dom';

import Modal from './RegisterModal.tsx';

interface LoginRequiredModalProps {
    onClose: () => void;
}

export default function LoginRequiredModal({
    onClose,
}: LoginRequiredModalProps) {
    const navigate = useNavigate();

    return (
        <Modal onClose={onClose}>
            <div
                css={css`
                    display: flex;
                    flex-direction: column;
                    gap: 2rem;
                    align-items: center;
                `}
            >
                로그인이 필요한 서비스입니다.
                <button css={btnStyles} onClick={() => navigate('/login')}>
                    로그인으로 이동
                </button>
            </div>
        </Modal>
    );
}

const { colors, typography } = theme;

const btnStyles = css`
    all: unset;
    width: 10rem;
    height: 2.5rem;
    border-radius: 1rem;
    background-color: ${colors.status.normal.excellent};
    color: ${colors.grey[100]};
    font-weight: ${typography.fontWeight.bold};
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
`;
