import { useState, useEffect } from "react";
import { Container, Table, Button, Modal, Form, Alert } from "react-bootstrap";
import { tagAPI } from "../../api/api";

function Tags() {
  const [tags, setTags] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingTag, setEditingTag] = useState(null);
  const [formData, setFormData] = useState({
    tagName: "",
    note: "",
  });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchTags();
  }, []);

  const fetchTags = async () => {
    try {
      const response = await tagAPI.getAll();
      setTags(response.data);
    } catch (error) {
      console.error("Error fetching tags:", error);
    }
  };

  const handleShowModal = (tag = null) => {
    if (tag) {
      setEditingTag(tag);
      setFormData({
        tagName: tag.tagName || "",
        note: tag.note || "",
      });
    } else {
      setEditingTag(null);
      setFormData({
        tagName: "",
        note: "",
      });
    }
    setShowModal(true);
    setError("");
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingTag(null);
    setFormData({
      tagName: "",
      note: "",
    });
    setError("");
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    console.log("Submitting tag data:", formData); // Debug log

    try {
      if (editingTag) {
        const response = await tagAPI.update(editingTag.tagId, formData);
        console.log("Update response:", response.data); // Debug log
      } else {
        const response = await tagAPI.create(formData);
        console.log("Create response:", response.data); // Debug log
      }
      await fetchTags();
      handleCloseModal();
    } catch (err) {
      console.error("Tag operation error:", err); // Debug log
      console.error("Error response:", err.response); // Debug log
      setError(
        err.response?.data?.message || err.response?.data || "Operation failed",
      );
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this tag?")) {
      try {
        await tagAPI.delete(id);
        await fetchTags();
      } catch (error) {
        alert("Cannot delete tag. It may be in use.");
      }
    }
  };

  return (
    <Container className="mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Tags</h2>
        <Button variant="primary" onClick={() => handleShowModal()}>
          Create Tag
        </Button>
      </div>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Tag Name</th>
            <th>Note</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {tags.map((tag) => (
            <tr key={tag.tagId}>
              <td>{tag.tagId}</td>
              <td>
                <span className="badge bg-primary">{tag.tagName}</span>
              </td>
              <td>{tag.note}</td>
              <td>
                <Button
                  variant="warning"
                  size="sm"
                  className="me-2"
                  onClick={() => handleShowModal(tag)}
                >
                  Edit
                </Button>
                <Button
                  variant="danger"
                  size="sm"
                  onClick={() => handleDelete(tag.tagId)}
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
          <Modal.Title>{editingTag ? "Edit Tag" : "Create Tag"}</Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            {error && <Alert variant="danger">{error}</Alert>}

            <Form.Group className="mb-3">
              <Form.Label>Tag Name *</Form.Label>
              <Form.Control
                type="text"
                name="tagName"
                value={formData.tagName}
                onChange={handleChange}
                required
                placeholder="e.g., Technology, Politics, Sports"
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Note</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                name="note"
                value={formData.note}
                onChange={handleChange}
                placeholder="Optional description or note about this tag"
              />
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Cancel
            </Button>
            <Button variant="primary" type="submit" disabled={loading}>
              {loading ? "Saving..." : editingTag ? "Update" : "Create"}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </Container>
  );
}

export default Tags;
