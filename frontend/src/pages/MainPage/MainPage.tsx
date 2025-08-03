import MountainCard from '../../components/organisms/MountainCard/MountainCard.tsx';
import styles from './MainPage.module.scss';
import SearchBar from '../../components/organisms/SearchBar/SearchBar.tsx';

const data = [
    {
        name: '설악산',
        weatherIconName: 'clear-day',
        surfaceTemperature: 20,
        summitTemperature: 15,
    },
    {
        name: '한라산',
        weatherIconName: 'partly-cloudy-day',
        surfaceTemperature: 25,
        summitTemperature: 20,
    },
    {
        name: '지리산',
        weatherIconName: 'rain',
        surfaceTemperature: 22,
        summitTemperature: 18,
    },
    {
        name: '오대산',
        weatherIconName: 'snow',
        surfaceTemperature: 19,
        summitTemperature: 14,
    },
    {
        name: '태백산',
        weatherIconName: 'thunderstorm',
        surfaceTemperature: 21,
        summitTemperature: 16,
    },
    {
        name: '덕유산',
        weatherIconName: 'yellow-dust',
        surfaceTemperature: 23,
        summitTemperature: 17,
    },
];

export default function MainPage() {
    const handleWheel = (event: React.WheelEvent<HTMLDivElement>) => {
        event.preventDefault();
        event.currentTarget.scrollLeft += Number(event.deltaY);
    };

    return (
        <>
            <SearchBar />
            <div className={styles.mountainCardContainer} onWheel={handleWheel}>
                {data.map((mountain) => (
                    <MountainCard
                        mountainName={mountain.name}
                        weatherIconName={mountain.weatherIconName}
                        surfaceTemperature={mountain.surfaceTemperature}
                        summitTemperature={mountain.summitTemperature}
                    />
                ))}
            </div>
        </>
    );
}
