# Social Media Content Calendar

A web-based Social Media Content Calendar for creating, managing, reviewing, searching and publishing social media content.

## MVP Features

- Create social media posts
- View all posts
- View individual posts
- Update posts
- Search posts by title, content or platform
- Filter posts by status
- Role-based content approval workflow
- Summary dashboard
- Scheduled date and time management
- Git-based feature branch collaboration

## Technology Stack

### Frontend
- React
- Vite
- JavaScript
- CSS

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Maven

### Database
- MySQL

## Role-Based Workflow

The MVP supports the following content workflow:

DRAFT → IN_REVIEW → APPROVED → PUBLISHED

A post can also be rejected during review:

IN_REVIEW → REJECTED

### Roles

| Role | Permission |
|---|---|
| Creator | Submit Draft for Review |
| Reviewer | Approve or Reject content |
| Publisher | Publish approved content |
| Admin | Perform valid workflow transitions |

## MVP Backlog

| ID | Task | Priority | Status |
|---|---|---|---|
| MVP-01 | Create social media posts | High | Done |
| MVP-02 | View social media posts | High | Done |
| MVP-03 | Update social media posts | High | Done |
| MVP-04 | Search social media posts | High | Done |
| MVP-05 | Filter posts by status | Medium | Done |
| MVP-06 | Role-based status workflow | High | Done |
| MVP-07 | Summary dashboard | High | Done |
| MVP-08 | React frontend content calendar | High | Done |
| MVP-09 | Second Git feature branch | High | Done |
| MVP-10 | Merge conflict creation | High | Done |
| MVP-11 | Merge conflict resolution | High | Done |
| MVP-12 | Release-ready source baseline | High | Done |

## API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/posts` | Create post |
| GET | `/api/posts` | View all posts |
| GET | `/api/posts/{id}` | View a specific post |
| PUT | `/api/posts/{id}` | Update post |
| GET | `/api/posts/search` | Search posts |
| GET | `/api/posts/status/{status}` | Filter by status |
| PATCH | `/api/posts/{id}/status` | Update post workflow status |
| GET | `/api/posts/dashboard/summary` | Get dashboard summary |

## Git Collaboration

The project uses Git feature branches and a `develop` integration branch.

The MVP implementation included:

- Feature branch development
- Second feature branch
- Intentional merge conflict
- Conflict resolution
- Merge into `develop`
- Release tagging

## Release

Current MVP release:

`v1.0.0`