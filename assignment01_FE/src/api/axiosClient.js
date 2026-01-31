import axios from "axios";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080/api",
  withCredentials: true, //enable session
  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosClient;