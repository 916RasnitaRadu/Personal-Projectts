import { useFormik } from "formik";
import { initialValues, validationSchema } from "./Form.constants";
import { User } from "src/domain/user";
import fmeImage from "./fme-image.png";
import "./Form.css";
import Button from "../../atoms/Buttons";
import Eye from "../../atoms/Icons";
import { useContext, useState } from "react";
import { AuthContext } from "../../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../../../services/UserService";

let user: User;

const LoginForm = () => {
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const formik = useFormik({
    initialValues,
    validationSchema,
    onSubmit: (values, actions) => {
      user = {
        username: values.username,
        password: values.password,
      };
      loginUser(user).then((loggedIn) => {
        if (loggedIn) {
          navigate("/home");
        } else {
          alert("Invalid credentials!");
          setTimeout(() => {
            navigate("/");
          }, 1000);
        }
      });

      formik.resetForm();
      actions.setSubmitting(false);
    },
  });

  const togglePassword = () => {
    setPasswordVisible(!passwordVisible);
  };

  const handleSubmit = (username: string, password: string) => {
    return;
  };

  return (
    <>
      <div className="container h-full md:mb-5">
        <div className="flex flex-wrap items-center justify-center text-[#343433] ">
          <div className="w-3/5 max-sm:w-96 max-md:w-9/12">
            <div className="block rounded-lg bg-white shadow-lg">
              <div className="g-0 lg:flex">
                {/* left column */}
                <div className="flex items-center justify-center shadow-inner lg:w-6/12 lg:py-20 bg-white  border-[#343433]">
                  <div className="text-center">
                    <img
                      className="mx-auto w-48 max-sm:w-28"
                      src={fmeImage}
                      alt="logo"
                    />
                    <h4 className="mb-12 mt-1 pb-1 text-xl max-sm:text-base font-semibold">
                      It's fme you love to work with!
                    </h4>
                  </div>
                </div>

                {/* right column */}
                <div className="py-10 lg:w-6/12 bg-[#343433]">
                  <div className="mx-3">
                    <div className="text-center">
                      <h4 className="mb-12 text-4xl font-semibold text-white">
                        Login
                      </h4>
                    </div>

                    <form
                      className="flex flex-col justify-center items-center text-white"
                      onSubmit={formik.handleSubmit}
                    >
                      <div className="relative z-0 w-3/4 mb-4 border-0 border-b-2 border-white outline-none">
                        <label htmlFor="username"></label>
                        <input
                          type="text"
                          name="username"
                          placeholder="username"
                          value={formik.values.username}
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur}
                          className="peer block min-h-[auto] w-full text-sm bg-transparent outline-none py-3 leading-[1.6] placeholder:text-gray-300"
                        />
                      </div>
                      {formik.touched.username && formik.errors.username ? (
                        <div className="text-red-400 relative z-0 w-3/4 mb-4">
                          {formik.errors.username}
                        </div>
                      ) : null}

                      <div className="flex z-0 w-3/4 mb-4 border-0 border-b-2 text-white border-white outline-none">
                        <label htmlFor="password"></label>
                        <input
                          type={passwordVisible ? "text" : "password"}
                          name="password"
                          id="password"
                          placeholder="password"
                          value={formik.values.password}
                          onChange={formik.handleChange}
                          onBlur={formik.handleBlur}
                          className="peer block min-h-[auto] w-full text-sm bg-transparent outline-none leading-[1.6] placeholder:text-gray-300"
                        />
                        <Button text="" method={togglePassword}>
                          <Eye />
                        </Button>
                      </div>
                      {formik.touched.password && formik.errors.password ? (
                        <div className="text-red-400 relative z-0 w-3/4 mb-4">
                          {formik.errors.password}
                        </div>
                      ) : null}

                      <div className="mt-12 flex justify-center text-center space-x-7">
                        <button
                          className="px-6 py-1 rounded-md text-white bg-[#343433] border-2 border-wite hover:text-[#343433] hover:bg-white hover:border-[#343433] hover:font-medium"
                          type="submit"
                        >
                          Sign in
                        </button>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default LoginForm;
