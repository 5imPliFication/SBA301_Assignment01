import { Navigate, Outlet } from "react-router-dom";

export default function ProtectedRoute() {
  const isLoggedIn = true; // TEMP — replace with /auth/me later

  return isLoggedIn ? <Outlet /> : <Navigate to="/login" />;
}