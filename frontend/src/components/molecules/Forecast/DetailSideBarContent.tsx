import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';
import { theme } from '../../../theme/theme';
import Icon from '../../atoms/Icon/Icons';

interface PropsState {
    iconName: string;
    title: string;
    value: string | React.ReactNode;
    description: string;
}

export default function DetailSideBarContent({
    iconName,
    title,
    value,
    description,
}: PropsState) {
    return (
        <div css={wrapperStyle}>
            <div css={headerStyles}>
                <div css={iconWrapperStyles}>
                    <Icon
                        name={iconName}
                        width={1.5}
                        height={1.5}
                        color='grey-60'
                    />
                </div>
                <CommonText
                    TextTag='span'
                    fontSize='body'
                    fontWeight='bold'
                    color='greyOpacity-60'
                >
                    {title}
                </CommonText>
            </div>
            <div css={contentStyles}>
                <CommonText
                    TextTag='span'
                    fontSize='label'
                    fontWeight='medium'
                    color='greyOpacity-60'
                >
                    {value}
                </CommonText>
                <CommonText
                    TextTag='span'
                    fontSize='label'
                    fontWeight='bold'
                    color='grey-10'
                >
                    {description}
                </CommonText>
            </div>
        </div>
    );
}

const { colors } = theme;

const headerStyles = css`
    display: flex;
    align-items: center;
`;

const wrapperStyle = css`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
`;

const contentStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
`;

const iconWrapperStyles = css`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 1.8rem;
    height: 1.8rem;
    margin-right: 0.5rem;
    border-radius: 100%;
    background-color: ${colors.grey[100]};
    border: 1px solid ${colors.greyOpacity[90]};
`;
