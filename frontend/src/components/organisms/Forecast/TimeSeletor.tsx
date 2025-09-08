import WeatherCell from '../../molecules/Forecast/WeatherCell.tsx';
import ToggleButton from '../../atoms/Button/ToggleButton.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import CommonText from '../../atoms/Text/CommonText.tsx';
import SelectorTitleText from '../../atoms/Text/SelectorTitle.tsx';
import Icon from '../../atoms/Icon/Icons.tsx';
import { useMemo } from 'react';
import { convertToIconName } from '../../../utils/utils.ts';
import { useDraggableScroll } from '../../../hooks/useDraggableScroll.ts';
import { useForecastByTime } from '../../../hooks/useForecastByTime.ts';
import { formatHour12 } from '../../templates/Forecast/helpers.ts';

interface PropsState {
    onToggle: () => void;
    isToggleOn: boolean;
    time: number;
    selectedMountainId: number;
    onTimeSelect?: (time: string) => void;
    scrollSelectedTime: string;
}

const { colors, typography } = theme;

export default function TimeSeletor({
    onToggle,
    isToggleOn,
    time,
    selectedMountainId,
    onTimeSelect,
    scrollSelectedTime,
}: PropsState) {
    const { forecastData, rawDataLength } = useForecastByTime({
        mountainId: selectedMountainId,
    });

    const dynamicScrollSizeStyles = css`
        width: ${time * 2 * 5.5 + 5}rem;
    `;

    function getStartAndEndTimeHoursOnly(
        scrollStartTimeStr: string,
        duration: number,
    ): [string, string] {
        const startDate = new Date(scrollStartTimeStr);

        const durationHours = Math.floor(duration);
        const endDate = new Date(
            startDate.getTime() + durationHours * 60 * 60 * 1000,
        );

        const startFormatted = formatHour12(startDate);
        const endFormatted = formatHour12(endDate);

        return [startFormatted, endFormatted];
    }

    const [startHourTime, endHourTime] = useMemo(
        () => getStartAndEndTimeHoursOnly(scrollSelectedTime, time * 2),
        [scrollSelectedTime, time],
    );
    const { scrollRef, startDrag, moveDrag, endDrag } = useDraggableScroll({
        data: forecastData,
        scrollSelectedTime,
        onTimeSelect,
        timeWindow: time * 2,
        rawDataLength: rawDataLength,
    });

    return (
        <div css={timeSeletorStyles}>
            <div css={headerStyles}>
                <div>
                    <SelectorTitleText>출발 시간 선택</SelectorTitleText>
                    <span
                        css={courseTimeStyles}
                    >{`왕복${time * 2}시간 코스`}</span>
                </div>
                <div>
                    <SelectorTitleText>고도 보정하기</SelectorTitleText>
                    <div css={tooltipWrapper}>
                        <Icon
                            name='info-circle'
                            width={1.5}
                            height={1.5}
                            color='grey-100'
                        />
                        <span>
                            {
                                '코스의 정상 고도를 반영해,\n기온과 풍속을 알려드려요.'
                            }
                        </span>
                    </div>
                    <ToggleButton
                        isOn={isToggleOn}
                        onClick={() => onToggle()}
                    />
                </div>
            </div>

            <div css={contentWrapperStyles}>
                <div
                    css={css`
                        height: 2.1rem;
                        overflow: hidden;
                    `}
                >
                    <div css={[scrollStyles, dynamicScrollSizeStyles]}>
                        <CommonText TextTag='span'>{startHourTime}</CommonText>
                        <CommonText TextTag='span'>{endHourTime}</CommonText>
                    </div>
                </div>

                <div
                    css={css`
                        width: 100%;
                        max-width: 100%;
                        overflow-x: hidden;
                        display: flex;
                        gap: 8px;
                        cursor: grab;
                        user-select: none;
                    `}
                    ref={scrollRef}
                    onMouseDown={(e) => startDrag(e.pageX)}
                    onMouseMove={(e) => moveDrag(e.pageX)}
                    onMouseUp={endDrag}
                    onMouseLeave={endDrag}
                    onTouchStart={(e) => startDrag(e.touches[0].pageX)}
                    onTouchMove={(e) => moveDrag(e.touches[0].pageX)}
                    onTouchEnd={endDrag}
                >
                    {forecastData.map((item, idx) => (
                        <WeatherCell
                            key={idx}
                            time={item.dateTime}
                            iconName={convertToIconName({
                                precipitationType: item.precipitationType,
                                sky: item.sky,
                            })}
                            temperature={item.temperature}
                        />
                    ))}
                </div>
            </div>
        </div>
    );
}

const timeSeletorStyles = css`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 0.75rem;
    box-sizing: border-box;
    width: 90%;
    max-width: 90rem;
    padding: 0.5rem 1.5rem 1.5rem 1.5rem;
    border-radius: 1.5rem;
    border: 1px solid ${colors.greyOpacityWhite[80]};
    background: ${colors.greyOpacityWhite[70]};
    backdrop-filter: blur(50px);
`;

const headerStyles = css`
    display: flex;
    justify-content: space-between;
    padding: 0.625rem 1rem;
    box-sizing: border-box;
    height: 3.2rem;
    width: 100%;
    border-bottom: 1px solid ${colors.greyOpacityWhite[80]};
    z-index: 20;

    & > div {
        display: flex;
        align-items: center;
        gap: 0.4rem;
    }
`;

const courseTimeStyles = css`
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.medium};
    line-height: 150%;
    color: ${colors.grey[90]};
    background-color: ${colors.greyOpacityWhite[80]};
    padding: 0.1rem 0.4rem;
    margin-left: 0.2rem;
    border-radius: 0.375rem;
    border: 1px solid ${colors.greyOpacityWhite[90]};
`;

const scrollStyles = css`
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: ${colors.greyOpacityWhite[80]};
    border-radius: 2rem;

    & span {
        position: relative;
        padding: 0 0.8rem;
        height: 2rem;
        background-color: ${colors.greyOpacityWhite[70]};
        border-radius: 2rem;
        line-height: 150%;
        z-index: 10;
    }
`;

const contentWrapperStyles = css`
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 1rem;
`;

const tooltipWrapper = css`
    position: relative;
    display: inline-block;

    & span {
        visibility: hidden;
        opacity: 0;
        width: 12rem;
        background-color: ${colors.grey[100]};
        color: ${colors.grey[0]};
        text-align: center;
        border-radius: 0.5rem;
        padding: 0.5rem;
        position: absolute;
        z-index: 99;
        bottom: 125%;
        left: 50%;
        transform: translateX(-50%);
        transition: opacity 0.3s;
        font-size: ${typography.fontSize.caption};
        white-space: pre-wrap;
        line-height: 1.4;
    }

    &:hover span,
    &:focus span {
        visibility: visible;
        opacity: 1;
    }
`;
