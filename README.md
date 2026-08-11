# Social Media Content Calendar

A web-based application for planning, organizing, scheduling, and managing social media content through a centralized content calendar.

## Tech Stack

| Component | Technology | Reason |
|---|---|---|
| Frontend | React.js + Tailwind CSS | Provides a responsive and user-friendly interface |
| Backend | Spring Boot (Java) | Builds secure and scalable REST APIs |
| Build Tool | Maven | Manages dependencies, builds, and project lifecycle |
| Database | MySQL | Stores users, posts, categories, and scheduling data |
| Version Control | Git | Tracks source code changes |
| Repository | GitHub | Remote repository and team collaboration |
| Continuous Integration | Jenkins | Automates build and testing |
| Testing | JUnit & Mockito | Unit and integration testing for Spring Boot |
| Containerization | Docker | Creates portable deployment environments |
| Deployment | Nginx + Spring Boot | Nginx acts as a reverse proxy while Spring Boot runs on embedded Tomcat |

## Project Structure

```text
social-media-content-calendar/
│
├── frontend/                 # React.js + Tailwind CSS
│
├── backend/                  # Spring Boot REST API
│
├── docs/                     # Project documentation
│
├── .github/
│   ├── ISSUE_TEMPLATE/
│   └── pull_request_template.md
│
├── .gitignore
├── CONTRIBUTING.md
└── README.md