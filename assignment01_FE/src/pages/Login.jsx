import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Form, Button, Alert, Card } from "react-bootstrap";
import { useAuth } from "../context/AuthContext";

function Login() {
  const [credentials, setCredentials] = useState({ email: "", password: "" });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleChange = (e) => {
    setCredentials({
      ...credentials,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      await login(credentials);
      navigate("/");
    } catch (err) {
      setError(err.response?.data || "Login failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container
      className="d-flex justify-content-center align-items-center"
      style={{ minHeight: "100vh" }}
    >
      <Card style={{ width: "400px" }}>
        <Card.Body>
          <h2 className="text-center mb-4">News Management System</h2>
          <h5 className="text-center mb-4 text-muted">Login</h5>
          {error && <Alert variant="danger">{error}</Alert>}
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                name="email"
                value={credentials.email}
                onChange={handleChange}
                placeholder="Enter your email"
                required
                autoFocus
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                name="password"
                value={credentials.password}
                onChange={handleChange}
                placeholder="Enter your password"
                required
              />
            </Form.Group>

            <Button
              variant="primary"
              type="submit"
              disabled={loading}
              className="w-100"
            >
              {loading ? "Logging in..." : "Login"}
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </Container>
  );
}

export default Login;
