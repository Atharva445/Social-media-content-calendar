import { useEffect, useState } from "react";
import "./App.css";

const API_URL = "http://localhost:8080/api/posts";

function App() {
  const [posts, setPosts] = useState([]);
  const [summary, setSummary] = useState({
    total: 0,
    draft: 0,
    inReview: 0,
    approved: 0,
    rejected: 0,
    published: 0,
  });

  const [search, setSearch] = useState("");
  const [loading, setLoading] = useState(false);

  const [showForm, setShowForm] = useState(false);
  const [editingPost, setEditingPost] = useState(null);

  const [role, setRole] = useState("CREATOR");

  const [formData, setFormData] = useState({
    title: "",
    content: "",
    platform: "Instagram",
    scheduledDate: "",
    scheduledTime: "",
  });

  // -----------------------------
  // FETCH POSTS
  // -----------------------------
  const fetchPosts = async () => {
    try {
      setLoading(true);

      const response = await fetch(API_URL);

      if (!response.ok) {
        throw new Error("Failed to fetch posts");
      }

      const data = await response.json();
      setPosts(data);
    } catch (error) {
      console.error(error);
      alert("Unable to load posts. Make sure the backend is running.");
    } finally {
      setLoading(false);
    }
  };

  // -----------------------------
  // FETCH DASHBOARD SUMMARY
  // -----------------------------
  const fetchSummary = async () => {
    try {
      const response = await fetch(`${API_URL}/dashboard/summary`);

      if (!response.ok) {
        throw new Error("Failed to fetch dashboard summary");
      }

      const data = await response.json();
      setSummary(data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchPosts();
    fetchSummary();
  }, []);

  // -----------------------------
  // SEARCH
  // -----------------------------
  const handleSearch = async () => {
    try {
      setLoading(true);

      if (!search.trim()) {
        await fetchPosts();
        return;
      }

      const response = await fetch(
        `${API_URL}/search?keyword=${encodeURIComponent(search)}`
      );

      if (!response.ok) {
        throw new Error("Search failed");
      }

      const data = await response.json();
      setPosts(data);
    } catch (error) {
      console.error(error);
      alert("Search failed.");
    } finally {
      setLoading(false);
    }
  };

  // -----------------------------
  // FORM INPUT
  // -----------------------------
  const handleInputChange = (e) => {
    const { name, value } = e.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  // -----------------------------
  // CREATE / UPDATE
  // -----------------------------
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const method = editingPost ? "PUT" : "POST";
      const url = editingPost
        ? `${API_URL}/${editingPost.id}`
        : API_URL;

      const body = {
        title: formData.title,
        content: formData.content,
        platform: formData.platform,
        scheduledDate: formData.scheduledDate,
        scheduledTime: formData.scheduledTime,
        status: "DRAFT",
      };

      const response = await fetch(url, {
        method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(body),
      });

      if (!response.ok) {
        throw new Error("Unable to save post");
      }

      setShowForm(false);
      setEditingPost(null);

      resetForm();

      await fetchPosts();
      await fetchSummary();

      alert(editingPost ? "Post updated successfully." : "Post created successfully.");
    } catch (error) {
      console.error(error);
      alert("Unable to save post.");
    }
  };

  // -----------------------------
  // EDIT
  // -----------------------------
  const handleEdit = (post) => {
    setEditingPost(post);

    setFormData({
      title: post.title,
      content: post.content,
      platform: post.platform,
      scheduledDate: post.scheduledDate,
      scheduledTime: post.scheduledTime,
    });

    setShowForm(true);
  };

  // -----------------------------
  // RESET FORM
  // -----------------------------
  const resetForm = () => {
    setFormData({
      title: "",
      content: "",
      platform: "Instagram",
      scheduledDate: "",
      scheduledTime: "",
    });
  };

  // -----------------------------
  // STATUS WORKFLOW
  // -----------------------------
  const getNextStatus = (status) => {
  if (role === "CREATOR" && status === "DRAFT") {
    return "IN_REVIEW";
  }

  if (role === "REVIEWER" && status === "IN_REVIEW") {
    return "APPROVED";
  }

  if (role === "PUBLISHER" && status === "APPROVED") {
    return "PUBLISHED";
  }

  return null;
};

  const handleStatusChange = async (post, targetStatus) => {
  try {
    const response = await fetch(
      `${API_URL}/${post.id}/status?status=${targetStatus}`,
      {
        method: "PATCH",
        headers: {
          "X-User-Role": role,
        },
      }
    );

    if (!response.ok) {
      const errorData = await response.json().catch(() => null);

      throw new Error(
        errorData?.message || "Unable to change post status"
      );
    }

    await fetchPosts();
    await fetchSummary();

  } catch (error) {
    console.error(error);
    alert(error.message);
  }
};

  // -----------------------------
  // STATUS COLOR
  // -----------------------------
  const getStatusClass = (status) => {
    switch (status) {
      case "DRAFT":
        return "status draft";

      case "IN_REVIEW":
        return "status review";

      case "APPROVED":
        return "status approved";

      case "REJECTED":
        return "status rejected";

      case "PUBLISHED":
        return "status published";

      default:
        return "status";
    }
  };

  return (
    <div className="app">

      {/* HEADER */}
      <header className="header">
        <div>
          <h1>Social Media Content Calendar</h1>
          <p>Manage, review and publish your social media content.</p>
        </div>

        <button
          className="primary-btn"
          onClick={() => {
            resetForm();
            setEditingPost(null);
            setShowForm(true);
          }}
        >
          + Create Post
        </button>
      </header>

      {/* DASHBOARD */}
      <section className="dashboard">

        <div className="summary-card">
          <span>Total Posts</span>
          <strong>{summary.total}</strong>
        </div>

        <div className="summary-card">
          <span>Draft</span>
          <strong>{summary.draft}</strong>
        </div>

        <div className="summary-card">
          <span>In Review</span>
          <strong>{summary.inReview}</strong>
        </div>

        <div className="summary-card">
          <span>Approved</span>
          <strong>{summary.approved}</strong>
        </div>

        <div className="summary-card">
          <span>Rejected</span>
          <strong>{summary.rejected}</strong>
        </div>

        <div className="summary-card">
          <span>Published</span>
          <strong>{summary.published}</strong>
        </div>

  </section>

      {/* CONTROLS */}
      <section className="controls">

        <div className="search-box">
          <input
            type="text"
            placeholder="Search by title, content or platform..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                handleSearch();
              }
            }}
          />

          <button onClick={handleSearch}>
            Search
          </button>

          <button
            className="secondary-btn"
            onClick={async () => {
              setSearch("");
              await fetchPosts();
            }}
          >
            Clear
          </button>
        </div>

        <div className="role-selector">
          <label>Current Role</label>

          <select
            value={role}
            onChange={(e) => setRole(e.target.value)}
          >
            <option value="CREATOR">Creator</option>
            <option value="REVIEWER">Reviewer</option>
            <option value="PUBLISHER">Publisher</option>
            <option value="ADMIN">Admin</option>
          </select>
        </div>

      </section>

      {/* POSTS TABLE */}
      <section className="posts-section">

        <div className="section-header">
          <div>
            <h2>Content Calendar</h2>
            <p>{posts.length} post(s) displayed</p>
          </div>
        </div>

        {loading ? (
          <div className="empty-state">
            Loading posts...
          </div>
        ) : posts.length === 0 ? (
          <div className="empty-state">
            No posts found.
          </div>
        ) : (
          <div className="table-wrapper">

            <table>

              <thead>
                <tr>
                  <th>Title</th>
                  <th>Platform</th>
                  <th>Scheduled</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>

                {posts.map((post) => (

                  <tr key={post.id}>

                    <td>
                      <div className="post-title">
                        {post.title}
                      </div>

                      <div className="post-content">
                        {post.content}
                      </div>
                    </td>

                    <td>
                      <span className="platform">
                        {post.platform}
                      </span>
                    </td>

                    <td>
                      <div>
                        {post.scheduledDate}
                      </div>

                      <small>
                        {post.scheduledTime}
                      </small>
                    </td>

                    <td>
                      <span className={getStatusClass(post.status)}>
                        {post.status.replace("_", " ")}
                      </span>
                    </td>

                    <td>

                      <div className="action-buttons">

                        <button
                          className="edit-btn"
                          onClick={() => handleEdit(post)}
                        >
                          Edit
                        </button>

                        {/* CREATOR */}
                        {role === "CREATOR" && post.status === "DRAFT" && (
                          <button
                            className="workflow-btn"
                            onClick={() =>
                              handleStatusChange(post, "IN_REVIEW")
                            }
                          >
                            Submit for Review
                          </button>
                        )}

                        {/* REVIEWER */}
                        {role === "REVIEWER" && post.status === "IN_REVIEW" && (
                          <>
                            <button
                              className="workflow-btn"
                              onClick={() =>
                                handleStatusChange(post, "APPROVED")
                              }
                            >
                              Approve
                            </button>

                            <button
                              className="reject-btn"
                              onClick={() =>
                                handleStatusChange(post, "REJECTED")
                              }
                            >
                              Reject
                            </button>
                          </>
                        )}

                        {/* PUBLISHER */}
                        {role === "PUBLISHER" && post.status === "APPROVED" && (
                          <button
                            className="workflow-btn"
                            onClick={() =>
                              handleStatusChange(post, "PUBLISHED")
                            }
                          >
                            Publish
                          </button>
                        )}

                      </div>

                    </td>

                  </tr>

                ))}

              </tbody>

            </table>

          </div>
        )}

      </section>

      {/* CREATE / UPDATE MODAL */}
      {showForm && (

        <div className="modal-overlay">

          <div className="modal">

            <div className="modal-header">

              <div>
                <h2>
                  {editingPost
                    ? "Update Post"
                    : "Create Post"}
                </h2>

                <p>
                  {editingPost
                    ? "Update your content details."
                    : "Add a new social media post."}
                </p>
              </div>

              <button
                className="close-btn"
                onClick={() => {
                  setShowForm(false);
                  setEditingPost(null);
                }}
              >
                ×
              </button>

            </div>

            <form onSubmit={handleSubmit}>

              <div className="form-group">

                <label>Title</label>

                <input
                  name="title"
                  value={formData.title}
                  onChange={handleInputChange}
                  placeholder="Enter post title"
                  required
                />

              </div>

              <div className="form-group">

                <label>Content</label>

                <textarea
                  name="content"
                  value={formData.content}
                  onChange={handleInputChange}
                  placeholder="Enter post content"
                  rows="5"
                  required
                />

              </div>

              <div className="form-row">

                <div className="form-group">

                  <label>Platform</label>

                  <select
                    name="platform"
                    value={formData.platform}
                    onChange={handleInputChange}
                  >
                    <option value="Instagram">
                      Instagram
                    </option>

                    <option value="Facebook">
                      Facebook
                    </option>

                    <option value="LinkedIn">
                      LinkedIn
                    </option>

                    <option value="Twitter">
                      Twitter
                    </option>
                  </select>

                </div>

                <div className="form-group">

                  <label>Scheduled Date</label>

                  <input
                    type="date"
                    name="scheduledDate"
                    value={formData.scheduledDate}
                    onChange={handleInputChange}
                    required
                  />

                </div>

              </div>

              <div className="form-group">

                <label>Scheduled Time</label>

                <input
                  type="time"
                  name="scheduledTime"
                  value={formData.scheduledTime}
                  onChange={handleInputChange}
                  required
                />

              </div>

              <div className="form-actions">

                <button
                  type="button"
                  className="secondary-btn"
                  onClick={() => {
                    setShowForm(false);
                    setEditingPost(null);
                  }}
                >
                  Cancel
                </button>

                <button
                  type="submit"
                  className="primary-btn"
                >
                  {editingPost
                    ? "Update Post"
                    : "Create Post"}
                </button>

              </div>

            </form>

          </div>

        </div>

      )}

    </div>
  );
}

export default App;