import { useState, useEffect } from "react";
import {
  Container,
  Table,
  Button,
  Modal,
  Form,
  Alert,
  Badge,
} from "react-bootstrap";
import { newsAPI, categoryAPI, tagAPI } from "../../api/api";

function NewsArticles() {
  const [articles, setArticles] = useState([]);
  const [categories, setCategories] = useState([]);
  const [tags, setTags] = useState([]); // Add this
  const [showModal, setShowModal] = useState(false);
  const [editingArticle, setEditingArticle] = useState(null);
  const [formData, setFormData] = useState({
    newsTitle: "",
    headline: "",
    newsContent: "",
    newsSource: "",
    newsStatus: true,
    categoryId: "",
    tagIds: [], // Add this
  });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchArticles();
    fetchCategories();
    fetchTags();
  }, []);

  const fetchArticles = async () => {
    try {
      const response = await newsAPI.getAll();
      setArticles(response.data);
    } catch (error) {
      console.error("Error fetching articles:", error);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await categoryAPI.getAll();
      setCategories(response.data);
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  const fetchTags = async () => {
    try {
      const response = await tagAPI.getAll();
      setTags(response.data);
    } catch (error) {
      console.error("Error fetching tags:", error);
    }
  };

  const handleShowModal = (article = null) => {
    if (article) {
      setEditingArticle(article);
      setFormData({
        newsTitle: article.newsTitle || "",
        headline: article.headline || "",
        newsContent: article.newsContent || "",
        newsSource: article.newsSource || "",
        newsStatus: article.newsStatus ?? true,
        categoryId: article.categoryId || "",
        tagIds: article.tagIds || [], // Add this
      });
    } else {
      setEditingArticle(null);
      setFormData({
        newsTitle: "",
        headline: "",
        newsContent: "",
        newsSource: "",
        newsStatus: true,
        categoryId: "",
        tagIds: [], // Add this
      });
    }
    setShowModal(true);
    setError("");
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingArticle(null);
    setFormData({
      newsTitle: "",
      headline: "",
      newsContent: "",
      newsSource: "",
      newsStatus: true,
      categoryId: "",
      tagIds: [],
    });
    setError("");
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });
  };

  // Add this handler for multi-select
  const handleTagChange = (e) => {
    const selectedOptions = Array.from(e.target.selectedOptions);
    const selectedTagIds = selectedOptions.map((option) =>
      parseInt(option.value),
    );
    setFormData({
      ...formData,
      tagIds: selectedTagIds,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      // Ensure tagIds is always an array (not a Set)
      const submitData = {
        ...formData,
        tagIds: Array.isArray(formData.tagIds)
          ? formData.tagIds
          : Array.from(formData.tagIds || []),
      };

      console.log("Submitting data:", submitData); // Debug log

      if (editingArticle) {
        await newsAPI.update(editingArticle.articleId, submitData);
      } else {
        await newsAPI.create(submitData);
      }
      await fetchArticles();
      handleCloseModal();
    } catch (err) {
      console.error("Submit error:", err); // Debug log
      setError(
        err.response?.data?.message || err.response?.data || "Operation failed",
      );
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this article?")) {
      try {
        await newsAPI.delete(id);
        await fetchArticles();
      } catch (error) {
        console.error("Error deleting article:", error);
      }
    }
  };

  return (
    <Container className="mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>News Articles</h2>
        <Button variant="primary" onClick={() => handleShowModal()}>
          Create Article
        </Button>
      </div>

      <Table striped bordered hover responsive>
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Headline</th>
            <th>Category</th>
            <th>Tags</th>
            <th>Source</th>
            <th>Status</th>
            <th>Author</th>
            <th>Created Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {articles.map((article) => (
            <tr key={article.newsId}>
              <td>{article.newsId}</td>
              <td>{article.newsTitle}</td>
              <td>{article.headline}</td>
              <td>{article.categoryName}</td>
              <td>
                {article.tagNames && article.tagNames.length > 0 ? (
                  article.tagNames.map((tagName, index) => (
                    <Badge key={index} bg="info" className="me-1">
                      {tagName}
                    </Badge>
                  ))
                ) : (
                  <span className="text-muted">No tags</span>
                )}
              </td>
              <td>{article.newsSource}</td>
              <td>
                <Badge bg={article.newsStatus ? "success" : "secondary"}>
                  {article.newsStatus ? "Active" : "Inactive"}
                </Badge>
              </td>
              <td>{article.authorName}</td>
              <td>{new Date(article.createdDate).toLocaleDateString()}</td>
              <td>
                <Button
                  variant="warning"
                  size="sm"
                  className="me-2"
                  onClick={() => handleShowModal(article)}
                >
                  Edit
                </Button>
                <Button
                  variant="danger"
                  size="sm"
                  onClick={() => handleDelete(article.newsId)}
                >
                  Delete
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      <Modal show={showModal} onHide={handleCloseModal} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>
            {editingArticle ? "Edit Article" : "Create Article"}
          </Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            {error && <Alert variant="danger">{error}</Alert>}

            <Form.Group className="mb-3">
              <Form.Label>News Title *</Form.Label>
              <Form.Control
                type="text"
                name="newsTitle"
                value={formData.newsTitle}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Headline *</Form.Label>
              <Form.Control
                type="text"
                name="headline"
                value={formData.headline}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>News Content *</Form.Label>
              <Form.Control
                as="textarea"
                rows={6}
                name="newsContent"
                value={formData.newsContent}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>News Source</Form.Label>
              <Form.Control
                type="text"
                name="newsSource"
                value={formData.newsSource}
                onChange={handleChange}
                placeholder="e.g., Reuters, AP News"
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Category *</Form.Label>
              <Form.Select
                name="categoryId"
                value={formData.categoryId}
                onChange={handleChange}
                required
              >
                <option value="">Select a category</option>
                {categories.map((category) => (
                  <option key={category.categoryId} value={category.categoryId}>
                    {category.categoryName}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Tags (Hold Ctrl/Cmd to select multiple)</Form.Label>
              <Form.Select
                multiple
                size={5}
                value={formData.tagIds}
                onChange={handleTagChange}
                style={{ minHeight: "120px" }}
              >
                {tags.map((tag) => (
                  <option key={tag.tagId} value={tag.tagId}>
                    {tag.tagName}
                  </option>
                ))}
              </Form.Select>
              <Form.Text className="text-muted">
                Hold Ctrl (Windows) or Cmd (Mac) to select multiple tags
              </Form.Text>
              {formData.tagIds.length > 0 && (
                <div className="mt-2">
                  <small>Selected: </small>
                  {formData.tagIds.map((tagId) => {
                    const tag = tags.find((t) => t.tagId === tagId);
                    return tag ? (
                      <Badge key={tagId} bg="info" className="me-1">
                        {tag.tagName}
                      </Badge>
                    ) : null;
                  })}
                </div>
              )}
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Check
                type="checkbox"
                name="newsStatus"
                label="Active Status"
                checked={formData.newsStatus}
                onChange={handleChange}
              />
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Cancel
            </Button>
            <Button variant="primary" type="submit" disabled={loading}>
              {loading ? "Saving..." : editingArticle ? "Update" : "Create"}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </Container>
  );
}

export default NewsArticles;
