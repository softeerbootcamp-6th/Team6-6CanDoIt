import DetailInfoSection from '../../components/templates/Forecast/DetailInfoSection.tsx';
import ForecastSearchSection from '../../components/templates/Forecast/ForecastSearchSection.tsx';
import SummaryInfoSection from '../../components/templates/Forecast/SummaryInfoSection.tsx';

export default function ForecastPage() {
    return (
        <div>
            <ForecastSearchSection />
            <SummaryInfoSection />
            <DetailInfoSection />
        </div>
    );
}
