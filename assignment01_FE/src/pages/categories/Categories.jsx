import { useState, useEffect } from 'react';
import { Container, Table, Button, Modal, Form, Alert, Badge } from 'react-bootstrap';
import { categoryAPI } from '../../api/api';

function Categories() {
  const [categories, setCategories] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingCategory, setEditingCategory] = useState(null);
  const [formData, setFormData] = useState({
    categoryName: '',
    categoryDescription: '',
    isActive: true,
    parentCategoryId: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const response = await categoryAPI.getAll();
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  const handleShowModal = (category = null) => {
    if (category) {
      setEditingCategory(category);
      setFormData({
        categoryName: category.categoryName || '',
        categoryDescription: category.categoryDescription || '',
        isActive: category.isActive ?? true,
        parentCategoryId: category.parentCategoryId || '',
      });
    } else {
      setEditingCategory(null);
      setFormData({
        categoryName: '',
        categoryDescription: '',
        isActive: true,
        parentCategoryId: '',
      });
    }
    setShowModal(true);
    setError('');
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingCategory(null);
    setFormData({
      categoryName: '',
      categoryDescription: '',
      isActive: true,
      parentCategoryId: '',
    });
    setError('');
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const submitData = {
        ...formData,
        parentCategoryId: formData.parentCategoryId === '' ? null : formData.parentCategoryId,
      };

      if (editingCategory) {
        await categoryAPI.update(editingCategory.categoryId, submitData);
      } else {
        await categoryAPI.create(submitData);
      }
      await fetchCategories();
      handleCloseModal();
    } catch (err) {
      setError(err.response?.data?.message || 'Operation failed');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this category?')) {
      try {
        await categoryAPI.delete(id);
        await fetchCategories();
      } catch (error) {
        alert('Cannot delete category. It may be in use.');
      }
    }
  };

  return (
    <Container className="mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Categories</h2>
        <Button variant="primary" onClick={() => handleShowModal()}>
          Create Category
        </Button>
      </div>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Parent Category</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {categories.map((category) => (
            <tr key={category.categoryId}>
              <td>{category.categoryId}</td>
              <td>{category.categoryName}</td>
              <td>{category.categoryDescription}</td>
              <td>{category.parentCategoryName || '-'}</td>
              <td>
                <Badge bg={category.isActive ? 'success' : 'secondary'}>
                  {category.isActive ? 'Active' : 'Inactive'}
                </Badge>
              </td>
              <td>
                <Button
                  variant="warning"
                  size="sm"
                  className="me-2"
                  onClick={() => handleShowModal(category)}
                >
                  Edit
                </Button>
                <Button
                  variant="danger"
                  size="sm"
                  onClick={() => handleDelete(category.categoryId)}
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
            {editingCategory ? 'Edit Category' : 'Create Category'}
          </Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            {error && <Alert variant="danger">{error}</Alert>}
            
            <Form.Group className="mb-3">
              <Form.Label>Category Name *</Form.Label>
              <Form.Control
                type="text"
                name="categoryName"
                value={formData.categoryName}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Category Description</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                name="categoryDescription"
                value={formData.categoryDescription}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Parent Category</Form.Label>
              <Form.Select
                name="parentCategoryId"
                value={formData.parentCategoryId}
                onChange={handleChange}
              >
                <option value="">None (Root Category)</option>
                {categories
                  .filter((cat) => !editingCategory || cat.categoryId !== editingCategory.categoryId)
                  .map((category) => (
                    <option key={category.categoryId} value={category.categoryId}>
                      {category.categoryName}
                    </option>
                  ))}
              </Form.Select>
              <Form.Text className="text-muted">
                Select a parent category to create a subcategory
              </Form.Text>
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Check
                type="checkbox"
                name="isActive"
                label="Active"
                checked={formData.isActive}
                onChange={handleChange}
              />
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Cancel
            </Button>
            <Button variant="primary" type="submit" disabled={loading}>
              {loading ? 'Saving...' : editingCategory ? 'Update' : 'Create'}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </Container>
  );
}

export default Categories;