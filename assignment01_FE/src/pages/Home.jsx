import { Container, Card, Row, Col } from 'react-bootstrap';
import { useAuth } from '../context/AuthContext';
import { Link } from 'react-router-dom';

function Home() {
  const { user } = useAuth();

  return (
    <Container className="mt-5">
      <h1 className="mb-4">Welcome to News Management System</h1>
      <p className="lead">Hello, {user?.name}!</p>

      <Row className="mt-5">
        <Col md={3}>
          <Card className="mb-3">
            <Card.Body>
              <Card.Title>News Articles</Card.Title>
              <Card.Text>
                Create, edit, and manage news articles.
              </Card.Text>
              <Link to="/news" className="btn btn-primary">
                Go to Articles
              </Link>
            </Card.Body>
          </Card>
        </Col>

        <Col md={3}>
          <Card className="mb-3">
            <Card.Body>
              <Card.Title>Categories</Card.Title>
              <Card.Text>
                Manage article categories and organization.
              </Card.Text>
              <Link to="/categories" className="btn btn-primary">
                Go to Categories
              </Link>
            </Card.Body>
          </Card>
        </Col>

        <Col md={3}>
          <Card className="mb-3">
            <Card.Body>
              <Card.Title>Tags</Card.Title>
              <Card.Text>
                Manage tags for categorizing content.
              </Card.Text>
              <Link to="/tags" className="btn btn-primary">
                Go to Tags
              </Link>
            </Card.Body>
          </Card>
        </Col>

        <Col md={3}>
          <Card className="mb-3">
            <Card.Body>
              <Card.Title>Accounts</Card.Title>
              <Card.Text>
                Manage system user accounts and permissions.
              </Card.Text>
              <Link to="/accounts" className="btn btn-primary">
                Go to Accounts
              </Link>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

export default Home;