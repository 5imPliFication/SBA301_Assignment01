import { useEffect, useState } from "react";
import { Form, Button } from "react-bootstrap";
import categoryApi from "../../api/category.api";
import tagApi from "../../api/tag.api";
import newsApi from "../../api/news.api";
import { useNavigate } from "react-router-dom";

export default function NewsForm() {
  const [categories, setCategories] = useState([]);
  const [tags, setTags] = useState([]);
  const navigate = useNavigate();

  const [form, setForm] = useState({
    newsTitle: "",
    headline: "",
    newsContent: "",
    newsSource: "",
    newsStatus: true,
    categoryId: null,
    tagIds: [],
  });

  useEffect(() => {
    categoryApi.getAll().then(res => setCategories(res.data));
    tagApi.getAll().then(res => setTags(res.data));
  }, []);

  const submit = async (e) => {
    e.preventDefault();
    await newsApi.create(form);
    navigate("/news");
  };

  return (
    <Form onSubmit={submit}>
      <Form.Control placeholder="Title"
        onChange={e => setForm({ ...form, newsTitle: e.target.value })} />

      <Form.Control placeholder="Headline"
        onChange={e => setForm({ ...form, headline: e.target.value })} />

      <Form.Control as="textarea" rows={5}
        onChange={e => setForm({ ...form, newsContent: e.target.value })} />

      <Form.Select onChange={e =>
        setForm({ ...form, categoryId: Number(e.target.value) })}>
        <option>Select category</option>
        {categories.map(c => (
          <option key={c.categoryId} value={c.categoryId}>
            {c.categoryName}
          </option>
        ))}
      </Form.Select>

      <Button type="submit">Create</Button>
    </Form>
  );
}