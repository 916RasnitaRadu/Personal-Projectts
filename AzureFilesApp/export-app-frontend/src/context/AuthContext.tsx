import { createContext, ReactNode, useState } from "react";

type Props = {
  children?: ReactNode;
};

type IAuthContext = {
  isAuthenticated: boolean;
  setAuthenticated: (newState: boolean) => void;
};

const initialValues = {
  isAuthenticated: false,
  setAuthenticated: () => {},
};

const AuthContext = createContext<IAuthContext>(initialValues);

const AuthProvider = ({ children }: Props) => {
  const [isAuthenticated, setAuthenticated] = useState(
    initialValues.isAuthenticated
  );

  return (
    <AuthContext.Provider
      value={{
        isAuthenticated,
        setAuthenticated,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export { AuthContext, AuthProvider };
