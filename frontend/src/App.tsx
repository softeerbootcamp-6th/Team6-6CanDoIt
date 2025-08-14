import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage/LoginPage';
import MainPage from './pages/MainPage/MainPage';
import ForecastPage from './pages/ForecastPage/ForecastPage';
import AlertPage from './pages/AlertPage/AletPage';
import ReportPage from './pages/ReportPage/ReportPage';
import MyPage from './pages/LoginPage/MyPage';
import Header from './components/organisms/Common/Header';
import RegisterPage from './pages/RegisterPage/RegisterPage';

function App() {
    return (
        <BrowserRouter>
            <Header />
            <Routes>
                <Route path='/' element={<MainPage />}></Route>
                <Route path='/forecast' element={<ForecastPage />}></Route>
                <Route path='/alert' element={<AlertPage />}></Route>
                <Route path='/login' element={<LoginPage />}></Route>
                <Route path='/register' element={<RegisterPage />}></Route>
                <Route path='/report' element={<ReportPage />}></Route>
                <Route path='/myPage' element={<MyPage />}></Route>
                {/* <Route path='*' element={<NotFound />}></Route> */}
            </Routes>
        </BrowserRouter>
    );
}

export default App;
