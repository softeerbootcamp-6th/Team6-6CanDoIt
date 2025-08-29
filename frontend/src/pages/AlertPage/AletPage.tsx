import { css } from '@emotion/react';
import AlertTableSection from '../../components/templates/Alert/AlertTableSection.tsx';
import AlertSearchSection from '../../components/templates/Alert/AlertSearchSection.tsx';

import data from './dummyData.tsx';

export default function AlertPage() {
    return (
        <div css={dummySteyls}>
            <AlertSearchSection />
            <AlertTableSection data={data} />
        </div>
    );
}

const dummySteyls = css`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;
