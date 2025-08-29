import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import CommonText from '../../atoms/Text/CommonText';
import Table from '../../organisms/Alert/Table';
import CautionText from '../../atoms/Text/CautionText';
import { useSearchParams } from 'react-router-dom';

interface Alert {
    altitude: string;
    caution: string;
    date: string;
    location: React.ReactNode; //
}

interface Mountain {
    name: string;
    id: number;
    alerts: Alert[];
}

interface AlertTableSectionProps {
    data: Mountain[];
}

export default function AlertTableSection({ data }: AlertTableSectionProps) {
    const [searchParams] = useSearchParams();
    const selectedMountainId = Number(searchParams.get('mountainid'));
    const selectedData = getMountainById(data, selectedMountainId);

    const tableData: Record<string, React.ReactNode>[] = selectedData
        ? selectedData.alerts.map((alert, index) => ({
              id: <span>{index + 1}</span>,
              name: <span>{selectedData.name}</span>,
              altitude: <span>{alert.altitude}</span>,
              caution: <CautionText>{alert.caution}</CautionText>,
              date: <span>{alert.date}</span>,
              location: alert.location,
          }))
        : [];

    return selectedMountainId ? (
        <div>
            <p css={pStyles}>
                현재
                <CommonText {...textProps}>{selectedData?.name}</CommonText>
                에는 주의해야 할 정보가
                <CommonText {...textProps}>
                    {selectedData?.alerts.length} 개가
                </CommonText>
                있어요.
            </p>
            <Table columns={columns} data={tableData} colWidths={colWidths} />
        </div>
    ) : null;
}

function getMountainById(data: Mountain[], id: number) {
    return data.find((data) => data.id === id);
}

const colWidths = ['7%', '21%', '18%', '18%', '18%', '18%'];
const columns = ['번호', '위치명', '해발고도', '주의내용', '발표일자', '위치'];

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
