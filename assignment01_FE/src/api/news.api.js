import axiosClient from "./axiosClient";

const newsApi = {
  getAll: () => axiosClient.get("/news"),
  getById: (id) => axiosClient.get(`/news/${id}`),
  create: (data) => axiosClient.post("/news", data),
};

export default newsApi;