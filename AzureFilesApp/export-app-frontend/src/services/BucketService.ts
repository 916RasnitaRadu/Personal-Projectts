import axios from "axios";
import { Bucket } from "../domain/bucket";
import { error } from "console";

interface BucketResponse {
  data: Bucket[];
  message: string;
}

export const getBuckets = (): Promise<BucketResponse | void> => {
  const token = localStorage.getItem("user-token");
  const url = "http://localhost:8080/buckets";

  const headers = {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
  return axios
    .get<BucketResponse>(url, { headers })
    .then((response) => {
      if (response.data && response.data.data) {
        return response.data;
      }
    })
    .catch((error) => {
      console.error("Error fetching bucket data:", error);
      throw error;
    });
  // const axiosInstance = axios.create({
  //   baseURL: "http://localhost:8080/buckets", // Your API base URL
  //   headers: {
  //     Authorization: `Bearer ${token}`,
  //     "Content-Type": "application/json", // Add other headers as needed
  //   },
  // });
  // const headers = {
  //   Authorization: `Bearer ${token}`,
  //   "Content-Type": "application/json",
  // };
  // return axiosInstance
  //   .get("")
  //   .then((response) => {
  //     // Handle the response data here
  //     console.log(response.data);
  //   })
  //   .catch((error) => {
  //     // Handle errors here
  //     console.error(error);
  //   });
};
