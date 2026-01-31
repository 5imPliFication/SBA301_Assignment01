import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Login from './pages/Login';
import Home from './pages/Home';
import NewsArticles from './pages/news/NewsArticles';
import Categories from './pages/categories/Categories';
import Tags from './pages/tags/Tags';
import Accounts from './pages/accounts/Accounts';
import Navigation from './components/Navigation';
import ProtectedRoute from './components/ProtectedRoute';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route
            path="/*"
            element={
              <ProtectedRoute>
                <Navigation />
                <Routes>
                  <Route path="/" element={<Home />} />
                  <Route path="/news" element={<NewsArticles />} />
                  <Route path="/categories" element={<Categories />} />
                  <Route path="/tags" element={<Tags />} />
                  <Route path="/accounts" element={<Accounts />} />
                </Routes>
              </ProtectedRoute>
            }
          />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;