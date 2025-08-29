import MapButton from '../../components/molecules/Button/MapButton';

const dataByMountain = [
    {
        id: 38,
        name: '계룡산',
        alerts: [
            {
                altitude: '845m',
                caution: '낙석주의',
                date: '2025-08-01',
                location: <MapButton />,
            },
            {
                altitude: '845m',
                caution: '강풍주의',
                date: '2025-08-02',
                location: <MapButton />,
            },
            {
                altitude: '845m',
                caution: '안개주의',
                date: '2025-08-03',
                location: <MapButton />,
            },
        ],
    },
    {
        name: '월악산',
        id: 46,
        alerts: [
            {
                altitude: '1099m',
                caution: '낙석주의',
                date: '2025-08-04',
                location: <MapButton />,
            },
            {
                altitude: '1099m',
                caution: '산불주의',
                date: '2025-08-05',
                location: <MapButton />,
            },
            {
                altitude: '1099m',
                caution: '강풍주의',
                date: '2025-08-06',
                location: <MapButton />,
            },
            {
                altitude: '1099m',
                caution: '안개주의',
                date: '2025-08-07',
                location: <MapButton />,
            },
        ],
    },
    {
        name: '치악산',
        id: 46,
        alerts: [
            {
                altitude: '1288m',
                caution: '낙석주의',
                date: '2025-08-08',
                location: <MapButton />,
            },
            {
                altitude: '1288m',
                caution: '강풍주의',
                date: '2025-08-09',
                location: <MapButton />,
            },
            {
                altitude: '1288m',
                caution: '산불주의',
                date: '2025-08-10',
                location: <MapButton />,
            },
        ],
    },
    {
        name: '북한산',
        id: 41,
        alerts: [
            {
                altitude: '836m',
                caution: '낙석주의',
                date: '2025-08-09',
                location: <MapButton />,
            },
            {
                altitude: '836m',
                caution: '강풍주의',
                date: '2025-08-11',
                location: <MapButton />,
            },
            {
                altitude: '836m',
                caution: '안개주의',
                date: '2025-08-12',
                location: <MapButton />,
            },
            {
                altitude: '836m',
                caution: '산불주의',
                date: '2025-08-13',
                location: <MapButton />,
            },
        ],
    },
    {
        name: '팔공산',
        id: 52,
        alerts: [
            {
                altitude: '1193m',
                caution: '낙석주의',
                date: '2025-08-14',
                location: <MapButton />,
            },
            {
                altitude: '1193m',
                caution: '산불주의',
                date: '2025-08-15',
                location: <MapButton />,
            },
            {
                altitude: '1193m',
                caution: '강풍주의',
                date: '2025-08-16',
                location: <MapButton />,
            },
        ],
    },
    {
        name: '태백산',
        id: 51,
        alerts: [
            {
                altitude: '1566m',
                caution: '낙석주의',
                date: '2025-08-17',
                location: <MapButton />,
            },
            {
                altitude: '1566m',
                caution: '산불주의',
                date: '2025-08-18',
                location: <MapButton />,
            },
            {
                altitude: '1566m',
                caution: '안개주의',
                date: '2025-08-19',
                location: <MapButton />,
            },
        ],
    },
    {
        name: '소백산',
        id: 43,
        alerts: [
            {
                altitude: '1445m',
                caution: '낙석주의',
                date: '2025-08-20',
                location: <MapButton />,
            },
            {
                altitude: '1445m',
                caution: '강풍주의',
                date: '2025-08-21',
                location: <MapButton />,
            },
            {
                altitude: '1445m',
                caution: '산불주의',
                date: '2025-08-22',
                location: <MapButton />,
            },
        ],
    },
    {
        name: '덕유산',
        id: 39,
        alerts: [
            {
                altitude: '1614m',
                caution: '낙석주의',
                date: '2025-08-23',
                location: <MapButton />,
            },
            {
                altitude: '1614m',
                caution: '강풍주의',
                date: '2025-08-24',
                location: <MapButton />,
            },
            {
                altitude: '1614m',
                caution: '안개주의',
                date: '2025-08-25',
                location: <MapButton />,
            },
        ],
    },
    {
        name: '속리산',
        id: 44,
        alerts: [
            {
                altitude: '1058m',
                caution: '낙석주의',
                date: '2025-08-26',
                location: <MapButton />,
            },
            {
                altitude: '1058m',
                caution: '산불주의',
                date: '2025-08-27',
                location: <MapButton />,
            },
            {
                altitude: '1058m',
                caution: '강풍주의',
                date: '2025-08-28',
                location: <MapButton />,
            },
        ],
    },
];

export default dataByMountain;
