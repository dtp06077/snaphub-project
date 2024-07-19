import React, { createContext, useState } from 'react'

export const LoginContext = createContext();
LoginContext.displayName = 'LoginContextName';

const LoginContextProvider = ({children}) => {
    // contest value : 로그인 여부, 로그아웃 함수
    const [isLogin, setLogin] = useState(false);

    const logout = () => {
        setLogin(false)
    }

    return (
            <LoginContext.Provider value={{isLogin, logout}}>
                {children}
            </LoginContext.Provider>
    )
}

export default LoginContextProvider