import axios from "axios";
import { User } from "src/domain/user";
import { useNavigate } from "react-router-dom";
import { ProtectedRoute } from "../components/util/ProtectedRoute";
import { NavigateFunction } from "react-router";

const url = "http://localhost:8080/users/login";

export const loginUser = async (user: User) => {
  try {
    const response = await axios.post(url, user);
    const token = response.data;
    localStorage.clear();
    localStorage.setItem("user-token", token.token);
    localStorage.getItem("user-token");

    if (ProtectedRoute(token.token)) {
      return true;
    }
    return false;
  } catch (error) {
    console.error("Error login in" + error);
  }
};
