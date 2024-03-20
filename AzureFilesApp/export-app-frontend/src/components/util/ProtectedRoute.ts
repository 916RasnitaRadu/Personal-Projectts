export const ProtectedRoute = (props: any) => {
  let isLoggedIn = false;
  const userToken = localStorage.getItem("user-token");
  if (!userToken || userToken === "undefined") {
    isLoggedIn = false;
  } else {
    isLoggedIn = true;
  }

  return isLoggedIn ? props : null;
};
