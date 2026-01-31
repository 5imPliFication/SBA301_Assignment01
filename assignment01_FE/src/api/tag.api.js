import axiosClient from "./axiosClient";

const tagApi = {
  getAll: () => axiosClient.get("/tags"),
};

export default tagApi;