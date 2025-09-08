import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import { useMemo } from 'react';
import { useDraggableScroll } from '../../../hooks/useDraggableScroll.ts';
import { useForecastByTime } from '../../../hooks/useForecastByTime.ts';

import { convertToIconName } from '../../../utils/utils.ts';
import { formatHour12 } from '../../templates/Forecast/helpers.ts';

import TimeSelectorHeader from '../../molecules/Forecast/TimeSeletorHeader.tsx';
import WeatherCell from '../../molecules/Forecast/WeatherCell.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';

interface PropsState {
    onToggle: () => void;
    isToggleOn: boolean;
    time: number;
    selectedMountainId: number;
    onTimeSelect?: (time: string) => void;
    scrollSelectedTime: string;
}

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
            <TimeSelectorHeader
                time={time}
                isToggleOn={isToggleOn}
                onToggle={onToggle}
            />

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

const { colors } = theme;

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
