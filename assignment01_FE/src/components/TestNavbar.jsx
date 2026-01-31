import { Link } from 'react-router-dom';
import { Navbar, Nav, NavDropdown, Form, FormControl, Button } from 'react-bootstrap';

const TestNavbar = () => {
  return (
    <Navbar bg="light" expand="lg">
        <Navbar.Brand as={Link} to="/">Test App</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto">
                <Nav.Link as={Link} to="/home">Home</Nav.Link>
                <Nav.Link as={Link} to="/about">About</Nav.Link>
                <NavDropdown title="Services" id="basic-nav-dropdown">
                    <NavDropdown.Item as={Link} to="/services/consulting">Consulting</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/services/development">Development</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/services/design">Design</NavDropdown.Item>
                </NavDropdown>
            </Nav>
            <Form inline>
                <FormControl type="text" placeholder="Search" className="mr-sm-2" />
                <Button variant="outline-success">Search</Button>
            </Form>
        </Navbar.Collapse>
    </Navbar>
  );
}
export default TestNavbar;