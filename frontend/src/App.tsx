import './App.scss';
import TextBox from './components/atoms/TextBox/TextBox.tsx';

function App() {
    return (
        <>
            <div>hello world</div>
            <TextBox
                title={'어디 날씨를 확인해볼까요?'}
                fontWeight={'regular'}
                typography={'title'}
            />
        </>
    );
}

export default App;
