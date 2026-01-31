import { Routes, Route } from "react-router-dom";
import Login from "../pages/Login";
import CategoryList from "../pages/categories/CategoryList";
import NewsList from "../pages/news/NewsList";
import NewsForm from "../pages/news/NewsForm";
import ProtectedRoute from "../components/ProtectedRoute.jsx";

export default function AppRouter() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />

      <Route element={<ProtectedRoute />}>
        <Route path="/categories" element={<CategoryList />} />
        <Route path="/news" element={<NewsList />} />
        <Route path="/news/create" element={<NewsForm />} />
      </Route>
    </Routes>
  );
}