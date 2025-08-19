import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import LoginPage from './pages/LoginPage/LoginPage';
import MainPage from './pages/MainPage/MainPage';
import ForecastPage from './pages/ForecastPage/ForecastPage';
import AlertPage from './pages/AlertPage/AletPage';
import ReportPage from './pages/ReportPage/ReportPage';
import Header from './components/organisms/Common/Header';
import RegisterPage from './pages/RegisterPage/RegisterPage';
import MyPage from './pages/MyPage/MyPage';

const queryClient = new QueryClient();

function App() {
    return (
        <QueryClientProvider client={queryClient}>
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
        </QueryClientProvider>
    );
}

export default App;
