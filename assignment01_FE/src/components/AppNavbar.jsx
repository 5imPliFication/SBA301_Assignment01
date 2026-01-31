import { Navbar, Nav, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const AppNavbar = () => {
  return (
    <Navbar bg="dark" variant="dark">
      <Container>
        <Navbar.Brand>News Admin</Navbar.Brand>
        <Nav>
          <Nav.Link as={Link} to="/news">News</Nav.Link>
          <Nav.Link as={Link} to="/categories">Categories</Nav.Link>
        </Nav>
      </Container>
    </Navbar>
  );
}

export default AppNavbar;