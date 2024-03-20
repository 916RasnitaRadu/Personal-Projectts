import { Routes as Router, Route, Navigate, Outlet } from "react-router-dom";
import LoginPage from "./components/pages/LoginPage/LoginPage";
import HomePage from "./components/pages/HomePage/HomePage";
import FileBrowserPage from "./components/pages/FileBrowserPage/FileBrowserPage";
import { useContext } from "react";
import { AuthContext } from "./context/AuthContext";

type Props = {};

const PrivateRoutes = () => {
  const { isAuthenticated } = useContext(AuthContext);

  if (!isAuthenticated) return <Navigate to="/" replace />;

  return <Outlet />;
};

const Routes = (props: Props) => {
  return (
    <Router>
      <Route path="/" element={<LoginPage />} />
      {/* <Route element={<PrivateRoutes />}> */}
      <Route path="/home" element={<HomePage />} />
      {/* <Route path="/fileBrowser" element={<FileBrowserPage />} /> */}
      <Route path="/fileBrowser/:bucketTitle" element={<FileBrowserPage />} />
      {/* </Route> */}
    </Router>
  );
};

export default Routes;
