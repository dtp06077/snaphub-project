import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import User from './pages/User';
import About from './pages/About';
import LoginContextProvider from './contexts/LoginContextProvider';

function App() {
    return (
        <BrowserRouter>
            <LoginContextProvider>
                <Routes>
                    <Route path="/" element={<Home />}></Route>
                    <Route path="/user" element={<User />}></Route>
                    <Route path="/about" element={<About />}></Route>
                </Routes>
            </LoginContextProvider>
        </BrowserRouter>
    );
}

export default App;
