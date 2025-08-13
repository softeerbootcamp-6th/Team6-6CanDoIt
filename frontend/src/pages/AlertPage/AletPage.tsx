import { css } from '@emotion/react';
import CautionText from '../../components/atoms/Text/CautionText';
import MapButton from '../../components/molecules/Button/MapButton.tsx';
import Table from '../../components/organisms/Alert/Table.tsx';
import CommonText from '../../components/atoms/Text/CommonText';
import { theme } from '../../theme/theme';
import SearchBar from '../../components/organisms/Common/SearchBar.tsx';
import Header from '../../components/organisms/Common/Header.tsx';

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

export default function AlertPage() {
    return (
        <div css={dummySteyls}>
            <Header />
            {/* 아직 SearchBar 구현이 덜되어있음 */}
            <SearchBar
                searchBarTitle='어디 안전정보를 확인해볼까요?'
                searchBarMessage='의 안전정보'
                isHomePage={false}
                mountainCourseData={mountainCourseData}
            />
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

const pStyles = css`
    color: ${colors.grey[100]};
    font-size: ${typography.fontSize.headline};
    font-weight: ${typography.fontWeight.medium};
    margin: 3rem 0 4rem 0;

    & span {
        margin-left: 0.6rem;
    }

    & span:last-of-type {
        margin-right: 0.6rem;
    }
`;

const dummySteyls = css`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

const textProps = {
    TextTag: 'span',
    color: 'grey-100',
    fontWeight: 'bold',
    fontSize: 'headline',
} as const;

const mountainCourseData = [
    {
        title: '산',
        options: ['설악산', '한라산', '지리산'],
    },
    {
        title: '코스',
        options: ['코스1', '코스2', '코스3'],
    },
];
