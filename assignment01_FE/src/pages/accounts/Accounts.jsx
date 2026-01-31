import { useState, useEffect } from 'react';
import { Container, Table, Button, Modal, Form, Alert } from 'react-bootstrap';
import { accountAPI } from '../../api/api';

function Accounts() {
  const [accounts, setAccounts] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingAccount, setEditingAccount] = useState(null);
  const [formData, setFormData] = useState({
    name: '',
    password: '',
    email: '',
    role: 2,
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchAccounts();
  }, []);

  const fetchAccounts = async () => {
    try {
      const response = await accountAPI.getAll();
      console.log('Accounts response:', response.data);
      setAccounts(response.data);
    } catch (error) {
      console.error('Error fetching accounts:', error);
    }
  };

  const handleShowModal = (account = null) => {
    if (account) {
      console.log('Editing account:', account);
      setEditingAccount(account);
      setFormData({
        name: account.name || '',
        password: '',
        email: account.email || '',
        role: account.role || 2,
      });
    } else {
      setEditingAccount(null);
      setFormData({ name: '', password: '', email: '', role: 2 });
    }
    setShowModal(true);
    setError('');
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingAccount(null);
    setFormData({ name: '', password: '', email: '', role: 2 });
    setError('');
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      if (editingAccount) {
        await accountAPI.update(editingAccount.id, formData);
      } else {
        await accountAPI.create(formData);
      }
      await fetchAccounts();
      handleCloseModal();
    } catch (err) {
      setError(err.response?.data?.message || 'Operation failed');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this account?')) {
      try {
        await accountAPI.delete(id);
        await fetchAccounts();
      } catch (error) {
        console.error('Error deleting account:', error);
      }
    }
  };

  return (
    <Container className="mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>System Accounts</h2>
        <Button variant="primary" onClick={() => handleShowModal()}>
          Create Account
        </Button>
      </div>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {accounts.map((account) => (
            <tr key={account.id}>
              <td>{account.id}</td>
              <td>{account.name}</td>
              <td>{account.email}</td>
              <td>{account.role}</td>
              <td>
                <Button
                  variant="warning"
                  size="sm"
                  className="me-2"
                  onClick={() => handleShowModal(account)}
                >
                  Edit
                </Button>
                <Button
                  variant="danger"
                  size="sm"
                  onClick={() => handleDelete(account.id)}
                >
                  Delete
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>
            {editingAccount ? 'Edit Account' : 'Create Account'}
          </Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form.Group className="mb-3">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
                required
                disabled={editingAccount}
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>
                Password {editingAccount && '(leave blank to keep current)'}
              </Form.Label>
              <Form.Control
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                required={!editingAccount}
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Role</Form.Label>
              <Form.Select name="role" value={formData.role} onChange={handleChange}>
                <option value={1}>ADMIN</option>
                <option value={2}>USER</option>
                <option value={3}>EDITOR</option>
              </Form.Select>
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Cancel
            </Button>
            <Button variant="primary" type="submit" disabled={loading}>
              {loading ? 'Saving...' : editingAccount ? 'Update' : 'Create'}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </Container>
  );
}

export default Accounts;