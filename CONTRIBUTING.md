# Contributing Guidelines

## Branch Policy

The project follows a feature-branch workflow.

| Branch | Purpose |
|---|---|
| `main` | Stable, production-ready code |
| `develop` | Integration branch |
| `feature/<name>` | New features |
| `fix/<name>` | Bug fixes |
| `docs/<name>` | Documentation changes |
| `refactor/<name>` | Code restructuring |

## Branch Naming Examples

### Features

```text
feature/user-authentication
feature/content-calendar
feature/post-management
feature/post-scheduling
```

### Bug Fixes

```text
fix/login-validation
fix/calendar-date-error
fix/post-status-error
```

### Documentation

```text
docs/api-documentation
docs/setup-guide
```

## Development Workflow

1. Create a branch from `develop`.
2. Implement the required changes.
3. Test the changes locally.
4. Commit using a meaningful commit message.
5. Push the branch to GitHub.
6. Create a Pull Request to `develop`.
7. Review and test the Pull Request.
8. Merge after verification.
9. Merge `develop` into `main` for stable releases.

## Commit Convention

Use the following format:

```text
type: description
```

### Commit Types

| Type | Purpose |
|---|---|
| `feat` | New feature |
| `fix` | Bug fix |
| `docs` | Documentation |
| `test` | Tests |
| `refactor` | Code restructuring |
| `chore` | Maintenance/configuration |

### Examples

```text
feat: add content calendar
fix: resolve post scheduling validation
docs: update project README
test: add post service tests
refactor: improve authentication service
chore: configure project structure
```

## Pull Requests

All feature and bug-fix changes should be submitted through Pull Requests.

Direct commits to `main` should be avoided.

Pull Requests should:

- Clearly describe the changes.
- Reference the related GitHub issue.
- Include testing information.
- Pass all required checks.
- Avoid committing passwords, API keys, or other sensitive information.