import * as Yup from "yup";

const initialValues = {
  username: "",
  password: "",
};

const validationSchema = Yup.object({
  username: Yup.string().required("Username is required!"),
  password: Yup.string().required("Password is required!"),
});

export { initialValues, validationSchema };
