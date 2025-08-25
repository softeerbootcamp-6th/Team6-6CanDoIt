import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import CommonText from '../../atoms/Text/CommonText';
import Table from '../../organisms/Alert/Table';
import MapButton from '../../molecules/Button/MapButton';
import CautionText from '../../atoms/Text/CautionText';

export default function AlertTableSection() {
    return (
        <div>
            <p css={pStyles}>
                현재
                <CommonText {...textProps}>설악산</CommonText>
                에는 주의해야 할 정보가
                <CommonText {...textProps}>3개</CommonText>
                있어요.
            </p>
            <Table columns={columns} data={updatedData} colWidths={colWidths} />
        </div>
    );
}
const data = [
    {
        번호: 1,
        위치명: '북한산',
        해발고도: '836m',
        주의내용: '낙석주의',
        발표일자: '2025-08-09',
        위치: <MapButton />,
    },
    {
        번호: 2,
        위치명: '지리산',
        해발고도: '1915m',
        주의내용: '낙석주의',
        발표일자: '2025-08-10',
        위치: <MapButton />,
    },
    {
        번호: 3,
        위치명: '설악산',
        해발고도: '1708m',
        주의내용: '낙석주의',
        발표일자: '2025-08-11',
        위치: <MapButton />,
    },
    {
        번호: 4,
        위치명: '한라산',
        해발고도: '1950m',
        주의내용: '낙석주의',
        발표일자: '2025-08-12',
        위치: <MapButton />,
    },
];

const colWidths = ['7%', '21%', '18%', '18%', '18%', '18%'];
const columns = ['번호', '위치명', '해발고도', '주의내용', '발표일자', '위치'];

const updatedData = data.map((item) => ({
    ...item,
    주의내용: <CautionText>{item.주의내용}</CautionText>,
}));

const { colors, typography } = theme;

const pStyles = css`
    color: ${colors.grey[100]};
    font-size: ${typography.fontSize.headline};
    font-weight: ${typography.fontWeight.medium};
    margin: 3rem 0 4rem 0;
    text-align: center;
    & span {
        margin-left: 0.6rem;
    }

    & span:last-of-type {
        margin-right: 0.6rem;
    }
`;

const textProps = {
    TextTag: 'span',
    color: 'grey-100',
    fontWeight: 'bold',
    fontSize: 'headline',
} as const;
