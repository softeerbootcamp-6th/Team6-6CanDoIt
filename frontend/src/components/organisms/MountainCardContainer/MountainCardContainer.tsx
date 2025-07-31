import MountainCard from '../../molecules/MountainCard/MountainCard.tsx';
import styles from './MountainCardContainer.module.scss';

const data = [
    {
        name: '설악산',
        surfaceTemperature: 20,
        summitTemperature: 15,
    },
    {
        name: '한라산',
        surfaceTemperature: 25,
        summitTemperature: 20,
    },
    {
        name: '지리산',
        surfaceTemperature: 22,
        summitTemperature: 18,
    },
    {
        name: '오대산',
        surfaceTemperature: 19,
        summitTemperature: 14,
    },
    {
        name: '태백산',
        surfaceTemperature: 21,
        summitTemperature: 16,
    },
    {
        name: '덕유산',
        surfaceTemperature: 23,
        summitTemperature: 17,
    },
];

export default function MountainCardContainer() {
    return (
        <div className={styles.mountainCardContainer}>
            {data.map((mountain) => (
                <MountainCard
                    mountainName={mountain.name}
                    surfaceTemperature={mountain.surfaceTemperature}
                    summitTemperature={mountain.summitTemperature}
                />
            ))}
        </div>
    );
}
