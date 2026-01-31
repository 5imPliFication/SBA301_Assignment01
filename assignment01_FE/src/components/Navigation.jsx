import { Navbar, Nav, Container, Button } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

function Navigation() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await logout();
      navigate('/login');
    } catch (error) {
      console.error('Logout error:', error);
    }
  };

  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Container>
        <Navbar.Brand as={Link} to="/">News Management</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/news">News Articles</Nav.Link>
            <Nav.Link as={Link} to="/categories">Categories</Nav.Link>
            <Nav.Link as={Link} to="/tags">Tags</Nav.Link>
            <Nav.Link as={Link} to="/accounts">Accounts</Nav.Link>
          </Nav>
          <Nav>
            {user && (
              <>
                <Navbar.Text className="me-3">
                  Signed in as: <strong>{user.name}</strong>
                </Navbar.Text>
                <Button variant="outline-light" size="sm" onClick={handleLogout}>
                  Logout
                </Button>
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Navigation;