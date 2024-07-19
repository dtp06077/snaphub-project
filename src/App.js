import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './pages/Home';
import Login from './pages/Login';
import Join from './pages/Join';
import User from './pages/User';
import About from './pages/About';

function App() {
    return (
        <BrowserRouter>
        <Routes>
            <Route path="/" element={ <Home />}></Route>
            <Route path="/login" element={ <Login />}></Route>
            <Route path="/join" element={ <Join />}></Route>
            <Route path="/user" element={ <User />}></Route>
            <Route path="/about" element={ <About />}></Route>
        </Routes>
        </BrowserRouter>
    );
}

export default App;
