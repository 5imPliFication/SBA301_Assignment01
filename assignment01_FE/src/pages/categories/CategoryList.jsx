import { useEffect, useState } from "react";
import { Table } from "react-bootstrap";
import categoryApi from "../../api/category.api";

export default function CategoryList() {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    categoryApi.getAll().then(res => setCategories(res.data));
  }, []);

  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Active</th>
          <th>Parent</th>
        </tr>
      </thead>
      <tbody>
        {categories.map(c => (
          <tr key={c.categoryId}>
            <td>{c.categoryId}</td>
            <td>{c.categoryName}</td>
            <td>{c.isActive ? "Yes" : "No"}</td>
            <td>{c.parentCategoryId ?? "-"}</td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}