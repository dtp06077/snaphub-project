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
            <Route path="/" elemen={ <Home />}></Route>
            <Route path="/login" elemen={ <Login />}></Route>
            <Route path="/join" elemen={ <Join />}></Route>
            <Route path="/user" elemen={ <User />}></Route>
            <Route path="/about" elemen={ <About />}></Route>
        </Routes>
        </BrowserRouter>
    );
}

export default App;
