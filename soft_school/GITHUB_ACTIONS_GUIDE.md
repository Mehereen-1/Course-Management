# GitHub Actions CI/CD Guide

Complete guide to using GitHub Actions for the Soft School project. This document explains how the automation pipelines work and how to manage them.

## Table of Contents

1. [Overview](#overview)
2. [Workflows](#workflows)
3. [How to Use](#how-to-use)
4. [Configuration](#configuration)
5. [Secrets & Environment](#secrets--environment)
6. [Troubleshooting](#troubleshooting)
7. [Best Practices](#best-practices)

---

## Overview

GitHub Actions automates testing, building, and deploying your code. The Soft School project uses three main workflows:

| Workflow | Trigger | Purpose |
|----------|---------|---------|
| **CI Pipeline** | Push to main/develop, PRs | Build, test, coverage |
| **Code Quality** | Push to main/develop, PRs | SonarQube analysis, security checks |
| **Release** | Git tags (v*) | Create releases, build Docker images |

---

## Workflows

### 1. CI Pipeline (`ci.yml`)

**Triggers:**
- Push to `main` or `develop` branch
- Pull requests to `main` branch

**Jobs:**
1. **Build and Test**
   - Checkout code
   - Setup Java 17 with Maven cache
   - Build with Maven (`clean verify`)
   - Generate code coverage report (JaCoCo)
   - Upload coverage to Codecov
   - Archive test results and coverage reports
   - Build Docker image
   - Publish test results

**Duration:** ~5-10 minutes

**Outputs (Artifacts):**
- Test results (XML reports)
- Code coverage reports (JaCoCo HTML)
- Docker image locally built

**Example Workflow Run:**
```
✓ Checkout repository
✓ Set up JDK 17
✓ Build and test with Maven
✓ Generate Code Coverage Report
✓ Upload Coverage to Codecov
✓ Archive Test Results
✓ Archive Code Coverage Report
✓ Build Docker Image
✓ Publish Test Results
```

### 2. Code Quality Analysis (`code-quality.yml`)

**Triggers:**
- Push to `main` or `develop` branch
- Pull requests to `main` branch

**Jobs:**

#### Job 1: SonarQube Analysis
- Analyzes code quality and maintainability
- Generates coverage metrics
- Detects code smells, bugs, vulnerabilities
- Requires: `SONAR_TOKEN` and `SONAR_HOST_URL` secrets

#### Job 2: Security Dependency Check
- Scans dependencies for known vulnerabilities
- Generates OWASP dependency check report
- Non-blocking (continues even if issues found)

**Duration:** ~10-15 minutes

**Outputs (Artifacts):**
- Dependency check report (HTML/XML)

**Requirements:**
- SonarQube server (self-hosted or SonarCloud)
- GitHub Secrets configured (see Configuration section)

### 3. Release & Deploy (`release.yml`)

**Triggers:**
- Git tag with pattern `v*` (e.g., `v1.0.0`, `v2.1.0`)

**Jobs:**
1. **Build and Release**
   - Build project with Maven
   - Extract version from tag
   - Create GitHub Release
   - Upload JAR file to release
   - Build Docker image with version tag

**Duration:** ~8-12 minutes

**Example Release Creation:**
```bash
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

This automatically triggers the release workflow.

---

## How to Use

### Viewing Workflow Status

1. **On GitHub Web Interface:**
   - Go to your repository → **Actions** tab
   - See all workflow runs with status (✓ Success, ✗ Failed, ⏳ Running)
   - Click on any run to see detailed logs

2. **In VS Code:**
   - Install "GitHub Actions" extension
   - View workflow status in the sidebar
   - Click to view logs directly in editor

3. **Status Badge:**
   Add this to your README.md to show current build status:
   ```markdown
   ![CI Pipeline](https://github.com/YOUR_ORG/soft-school/actions/workflows/ci.yml/badge.svg?branch=main)
   ![Code Quality](https://github.com/YOUR_ORG/soft-school/actions/workflows/code-quality.yml/badge.svg?branch=main)
   ```

### Running Workflow Manually

You can manually trigger a workflow from the GitHub UI:

1. Go to **Actions** tab
2. Select desired workflow
3. Click **Run workflow** button
4. Choose branch and click **Run workflow**

### Downloading Artifacts

Artifacts (test results, coverage reports) are available for download:

1. Go to **Actions** tab
2. Click on completed workflow run
3. Scroll to **Artifacts** section
4. Download desired artifact (ZIP file)

### Viewing Test Results

After CI pipeline completes:

1. Go to workflow run details
2. Click **Publish Test Results** step
3. See summary of tests (passed/failed/skipped)
4. Download `test-results` artifact for detailed XML reports

### Viewing Code Coverage

After CI pipeline completes:

1. Download `code-coverage-report` artifact
2. Extract ZIP file
3. Open `index.html` in browser
4. View detailed coverage metrics by file

---

## Configuration

### GitHub Secrets Setup

Some workflows require GitHub Secrets to function properly.

**How to Add Secrets:**

1. Go to repository → **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add the secret name and value
4. Click **Add secret**

**Required Secrets:**

#### For Code Quality Workflow:
- `SONAR_TOKEN` - Your SonarQube/SonarCloud token
  - Get from: https://sonarcloud.io/account/security/
  
- `SONAR_HOST_URL` - URL to your SonarQube server
  - For SonarCloud: `https://sonarcloud.io`
  - For self-hosted: `https://your-sonarqube-url:9000`

#### For Docker Registry (Future):
- `DOCKER_HUB_USERNAME` - Docker Hub username
- `DOCKER_HUB_PASSWORD` - Docker Hub access token
- `DOCKER_HUB_REPO` - Docker Hub repository name

**Example: Setting up SonarCloud**

1. Sign up at https://sonarcloud.io with GitHub account
2. Create organization and project
3. Get authentication token from Account → Security
4. Add `SONAR_TOKEN` secret in GitHub repository settings
5. Workflows will now automatically report to SonarCloud

### Environment Variables

Workflows use environment variables for PostgreSQL connection:

```yaml
env:
  SPRING_DATASOURCE_URL: jdbc:postgresql://localhost:5432/soft_school
  SPRING_DATASOURCE_USERNAME: postgres
  SPRING_DATASOURCE_PASSWORD: postgres
```

These are set in the workflow YAML files. Modify in `.github/workflows/ci.yml` if your database credentials differ.

### Workflow Permissions

For GitHub Script actions (PR comments), ensure repository has proper permissions:

1. Go to **Settings** → **Actions** → **General**
2. Under "Workflow permissions", select:
   - ✓ Read and write permissions
   - ✓ Allow GitHub Actions to create and approve pull requests

---

## Troubleshooting

### Build Failures

**Problem:** Maven build fails with "Cannot find module"

**Solutions:**
1. Check Java version is 17:
   ```yaml
   java-version: '17'
   ```
2. Verify cache is working:
   ```yaml
   cache: maven
   ```
3. Run locally to replicate:
   ```bash
   ./mvnw clean verify
   ```

### Database Connection Issues

**Problem:** Tests fail with "Cannot connect to PostgreSQL"

**Solutions:**
1. Verify PostgreSQL service is configured in workflow
2. Check database credentials match:
   ```yaml
   POSTGRES_PASSWORD: postgres
   SPRING_DATASOURCE_PASSWORD: postgres
   ```
3. Ensure health check is passing:
   ```
   --health-cmd="pg_isready -U postgres"
   ```

### Code Coverage Not Uploading

**Problem:** Codecov reports not appearing

**Solutions:**
1. Codecov action may need repository token:
   ```yaml
   token: ${{ secrets.CODECOV_TOKEN }}
   ```
2. Check JaCoCo report was generated:
   ```
   Run section "Generate Code Coverage Report" in logs
   ```
3. Verify file path is correct:
   ```
   ./target/site/jacoco/jacoco.xml
   ```

### SonarQube Analysis Failing

**Problem:** SonarQube scan fails with "Unauthorized"

**Solutions:**
1. Verify `SONAR_TOKEN` secret is set in repository
2. Check token is not expired (regenerate if needed)
3. For SonarCloud, ensure organization is set correctly:
   ```bash
   -Dsonar.organization=your-organization
   ```

### Release Workflow Not Triggered

**Problem:** Pushing git tag doesn't trigger release workflow

**Solutions:**
1. Verify tag format matches pattern `v*`:
   ```bash
   git tag -a v1.0.0 -m "Release 1.0.0"
   git push origin v1.0.0
   ```
2. Check workflow file exists: `.github/workflows/release.yml`
3. Verify `on.push.tags` section is configured:
   ```yaml
   on:
     push:
       tags:
         - 'v*'
   ```

### Workflow Stuck or Timing Out

**Problem:** Workflow runs for more than 30 minutes (default GitHub limit)

**Solutions:**
1. Check for long-running tests - run in parallel:
   ```yaml
   strategy:
     matrix:
       test-module: [unit, integration]
   ```
2. Add timeout to prevent hanging:
   ```yaml
   timeout-minutes: 30
   ```
3. Check logs for infinite loops or blocking operations

---

## Best Practices

### 1. Use Caching

Cache Maven dependencies to speed up builds:
```yaml
- uses: actions/setup-java@v4
  with:
    java-version: '17'
    distribution: 'temurin'
    cache: maven  # This caches ~/.m2
```

**Results:** 40-60% faster builds

### 2. Fail Fast

Use `continue-on-error: true` selectively. Critical jobs should fail fast:
```yaml
- name: Build and test
  run: ./mvnw clean verify
  # No continue-on-error - fail if build breaks

- name: Code quality (non-blocking)
  run: ./mvnw sonar:sonar
  continue-on-error: true  # Don't block PR if quality issues found
```

### 3. Version Everything

Use specific versions for actions to prevent unexpected breaks:
```yaml
uses: actions/checkout@v4      # ✓ Specific version
uses: actions/setup-java@v4    # ✓ Specific version
uses: actions/checkout@master  # ✗ Avoid master/main
```

### 4. Use Matrix for Parallel Testing

Test multiple configurations simultaneously:
```yaml
strategy:
  matrix:
    java-version: ['17', '21']
    os: [ubuntu-latest, windows-latest]
runs-on: ${{ matrix.os }}
```

### 5. Archive Important Results

Always archive test/coverage reports for history:
```yaml
- uses: actions/upload-artifact@v3
  with:
    name: test-results
    path: target/surefire-reports/
    retention-days: 90
```

### 6. Document Workflow Changes

When modifying workflows, document the change:
```yaml
# Added JaCoCo coverage generation - PR #45
- name: Generate Code Coverage Report
  run: ./mvnw jacoco:report
```

### 7. Monitor Workflow Performance

Regularly review:
- Average build time (target: < 10 minutes)
- Failure rate (target: < 5%)
- Artifact storage usage

Go to **Settings** → **Actions** → **General** to view runner statistics.

### 8. Use Environment-Specific Secrets

For deployment workflows, use environment secrets:
```yaml
environment:
  name: production
  url: https://soft-school.example.com
```

Then reference environment-specific secrets:
```yaml
- run: deploy.sh
  env:
    DEPLOY_KEY: ${{ secrets.PROD_DEPLOY_KEY }}
```

---

## Advanced Configurations

### Conditional Job Execution

Run jobs only on specific branches:
```yaml
jobs:
  build:
    if: github.ref == 'refs/heads/main'
    runs-on: ubuntu-latest
```

Or only on pull requests:
```yaml
jobs:
  lint:
    if: github.event_name == 'pull_request'
    runs-on: ubuntu-latest
```

### Multi-Step Testing

Run different test categories:
```yaml
- name: Unit Tests
  run: ./mvnw test -Dtest="*Test"

- name: Integration Tests
  run: ./mvnw test -Dtest="*IT"

- name: E2E Tests
  run: ./mvnw test -Dtest="*E2E"
```

### Deploy on Success

Deploy only after successful build:
```yaml
- name: Deploy
  if: success() && github.ref == 'refs/heads/main'
  run: |
    docker push softschool:latest
    ./scripts/deploy.sh
```

### Skip Workflow for Certain Commits

Add `[skip ci]` to commit message:
```bash
git commit -m "Update README [skip ci]"
git push
```

---

## Common Commands

### Manually trigger workflow (CLI)

```bash
gh workflow run ci.yml --ref main
```

### List all workflow runs

```bash
gh run list --workflow=ci.yml
```

### View workflow logs

```bash
gh run view <run-id> --log
```

### Download artifacts

```bash
gh run download <run-id> --name test-results
```

---

## Summary

**Quick Reference:**

| Task | Steps |
|------|-------|
| **View workflow status** | Actions tab → Click workflow |
| **Download test results** | Actions → Click run → Download artifact |
| **View code coverage** | Download report, open `index.html` |
| **Add secrets** | Settings → Secrets → New secret |
| **Create release** | `git tag -a v1.0.0 && git push origin v1.0.0` |
| **Fix failed workflow** | Check logs, fix code, push commit |

**Support:** Check workflow logs for detailed error messages. GitHub Actions status page: github.com/status

