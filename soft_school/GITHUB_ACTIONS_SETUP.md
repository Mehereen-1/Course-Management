# GitHub Actions Workflow - Step-by-Step Setup Guide

This guide walks you through setting up and using GitHub Actions for the Soft School project, from initial configuration to monitoring builds.

## Part 1: Initial Setup

### Step 1: Push Code to GitHub

First, ensure your code is on GitHub:

```bash
# Initialize git (if not already done)
git init

# Add all files
git add .

# Create initial commit
git commit -m "Initial project setup with testing and documentation"

# Add remote repository (replace with your repo URL)
git remote add origin https://github.com/YOUR_USERNAME/soft-school.git

# Push to GitHub
git branch -M main
git push -u origin main
```

### Step 2: Verify Workflows Are Present

Check that workflow files exist in your repository:

```bash
# View workflow files
ls -la .github/workflows/

# Expected output:
# - ci.yml                    # Main build and test pipeline
# - code-quality.yml          # SonarQube analysis
# - release.yml               # Release automation
```

### Step 3: Configure Repository Secrets

Some workflows need secrets to function. Set them up now:

**For Code Quality Analysis (Optional but Recommended):**

1. Go to: `https://github.com/YOUR_USERNAME/soft-school/settings/secrets/actions`

2. Click **New repository secret**

3. Add secret: `SONAR_TOKEN`
   - Get token from: https://sonarcloud.io/account/security/
   - Click **Generate Tokens**
   - Copy the token value
   - Paste into GitHub secret

4. Add second secret: `SONAR_HOST_URL`
   - Value: `https://sonarcloud.io` (for SonarCloud)

**Validation:**
- If you skip this, CI pipeline still works (code quality is non-blocking)
- Only SonarQube jobs will be skipped

---

## Part 2: Using CI Pipeline

### Scenario 1: Pushing Code to Main Branch

When you push code, CI pipeline automatically runs:

```bash
# Make some code changes
echo "// New feature" >> src/main/java/com/example/SomeFile.java

# Commit and push
git add .
git commit -m "Add new feature"
git push origin main
```

**What happens automatically:**
1. GitHub detects push to main branch
2. CI workflow starts (visible in Actions tab)
3. PostgreSQL service starts
4. Java 17 is set up
5. Maven builds project (`clean verify`)
6. All tests run
7. Code coverage report generated
8. Results uploaded to Codecov
9. Docker image built

**Monitor the build:**

1. Go to: `https://github.com/YOUR_USERNAME/soft-school/actions`

2. Click on the latest workflow run

3. View real-time logs:
   - Click **Build and Test** job
   - Scroll through steps to see progress
   - Look for green checkmarks (✓) for successes
   - Red X (✗) for failures

**What to look for:**
```
✓ Checkout repository         (0s)
✓ Set up JDK 17              (15s)
✓ Build and test with Maven  (180s) ← Most time-consuming
✓ Generate Code Coverage     (30s)
✓ Upload Coverage to Codecov (10s)
✓ Archive Test Results       (5s)
✓ Build Docker Image         (20s)
```

**If build fails:**
1. Click **Build and test with Maven** step
2. Scroll to find error message
3. Common issues:
   - Database connection: Check PostgreSQL service
   - Test failure: Run `./mvnw test` locally
   - Compilation error: Fix code and push again

### Scenario 2: Making a Pull Request

When creating a PR, CI runs automatically:

```bash
# Create feature branch
git checkout -b feature/new-feature

# Make changes
echo "// New implementation" >> src/main/java/com/example/NewFeature.java

# Commit and push
git add .
git commit -m "Implement new feature"
git push origin feature/new-feature
```

Then create PR on GitHub:

1. Go to your repository
2. Click **Compare & pull request** (appears automatically after push)
3. Add title and description
4. Click **Create pull request**

**GitHub will:**
- Show CI status as "pending"
- Run all tests and build checks
- Display result as ✓ All checks passed or ✗ Some checks failed
- Block merging if builds fail (if protection rules enabled)

**View PR checks:**
1. Go to PR page
2. Scroll down to "Checks" section
3. Click **Details** next to failed checks
4. View specific error logs

### Scenario 3: Downloading Test Results

After CI completes, download detailed reports:

**Download Test Results:**

1. Go to: Actions → Latest CI run
2. Scroll down to **Artifacts** section
3. Click **test-results** → Download
4. Extract ZIP file
5. Open `target/surefire-reports/index.html` in browser
6. View test summary with pass/fail counts

**Download Code Coverage:**

1. Go to: Actions → Latest CI run
2. Click **code-coverage-report** artifact
3. Extract ZIP file
4. Open `target/site/jacoco/index.html` in browser
5. View coverage:
   - Line coverage percentage
   - Branch coverage percentage
   - Covered/uncovered lines by file

---

## Part 3: Using Code Quality Workflow

### How It Works

Code Quality workflow runs:
- After each push to main/develop
- On pull requests to main
- Analyzes code quality using SonarQube
- Scans dependencies for vulnerabilities

### Viewing Code Quality Results

**On SonarCloud Dashboard:**

1. Go to: https://sonarcloud.io/dashboard
2. Find your "soft-school" project
3. View metrics:
   - Coverage percentage
   - Code smells (places to improve)
   - Security hotspots
   - Technical debt

**On GitHub:**

1. Go to Actions tab
2. Click **Code Quality Analysis** workflow
3. Click latest run
4. View "SonarQube Scan" step output

### Interpreting Results

**Example Output:**
```
Coverage: 75%              ← Goal: > 80%
Bugs: 2                    ← Fix before merge
Code Smells: 15            ← Consider refactoring
Duplications: 3.2%         ← Keep below 5%
Security Issues: 0         ← Good!
```

**Taking Action:**

If issues found:
1. Go to SonarCloud dashboard
2. Click on project
3. Review issues by file
4. Fix code locally
5. Push changes
6. Re-run analysis automatically

---

## Part 4: Creating Releases

### Creating a Release Tag

Create releases by tagging code:

```bash
# Ensure code is committed and pushed
git status
git push origin main

# Create annotated tag
git tag -a v1.0.0 -m "Release version 1.0.0 - Initial production release"

# Push tag to GitHub
git push origin v1.0.0
```

**What happens automatically:**

1. GitHub detects tag with format `v*`
2. Release workflow starts
3. Builds project
4. Creates GitHub Release page
5. Uploads JAR file to release
6. Builds Docker image with version tag
7. Marks as production-ready

### Monitoring Release Build

**View Release Workflow:**

1. Go to: Actions tab
2. Click **Release and Deploy** workflow
3. Click latest run
4. Watch build progress

**Example timeline:**
```
✓ Checkout code              (5s)
✓ Set up JDK 17             (10s)
✓ Build with Maven          (120s)
✓ Extract version            (1s)
✓ Create Release             (2s)
✓ Upload JAR to Release     (5s)
✓ Build Docker Image        (30s)
```

### Accessing Released Artifacts

**Via GitHub Releases Page:**

1. Go to: `https://github.com/YOUR_USERNAME/soft-school/releases`
2. Find your release (e.g., `v1.0.0`)
3. Download JAR file from release
4. Use for deployment or distribution

**Example Release Page:**
```
Release v1.0.0
Release version 1.0.0 - Initial production release

Assets:
- soft-school-1.0.0.jar (45.2 MB)
- Source code (zip)
- Source code (tar.gz)
```

---

## Part 5: Advanced: Manual Workflow Triggers

### Manually Run CI Pipeline

Run CI without pushing code (useful for testing workflows):

**Method 1: Via GitHub UI**

1. Go to: Actions tab
2. Click **CI Pipeline** in left sidebar
3. Click **Run workflow** button
4. Select branch (main/develop)
5. Click **Run workflow**

**Method 2: Via GitHub CLI (Advanced)**

```bash
# Install GitHub CLI: https://cli.github.com/

# Authenticate
gh auth login

# Run workflow
gh workflow run ci.yml --ref main

# List recent runs
gh run list --workflow=ci.yml

# View specific run
gh run view <run-id> --log
```

### Canceling Running Workflows

If workflow is taking too long or failed to trigger correctly:

**Via GitHub UI:**

1. Go to: Actions tab
2. Click running workflow
3. Click **Cancel workflow** button

**Via CLI:**

```bash
gh run cancel <run-id>
```

---

## Part 6: Troubleshooting Guide

### Problem 1: Build Fails with "Cannot find symbol"

**Symptoms:**
- Error: "cannot find symbol"
- Step: "Build and test with Maven"

**Solution:**

```bash
# Run locally to verify
./mvnw clean verify

# If fails locally, it's a code issue
# If passes locally, might be environment issue

# Try clearing cache in GitHub Actions:
# Go to Settings → Actions → General → "Remove all"
```

### Problem 2: Tests Fail in CI But Pass Locally

**Symptoms:**
- Tests pass with `./mvnw test` locally
- Tests fail in GitHub Actions

**Common causes:**
- Database timing issues (PostgreSQL not ready)
- Path/case sensitivity on Linux (CI runs on Ubuntu)
- Environment variables differ

**Solution:**

```bash
# Add wait for database in workflow
- name: Wait for PostgreSQL
  run: |
    until pg_isready -h localhost -p 5432; do
      echo "Waiting for PostgreSQL..."
      sleep 1
    done

# Or increase health check timeout in ci.yml:
--health-timeout=10s
--health-retries=10
```

### Problem 3: Code Coverage Not Showing

**Symptoms:**
- Codecov badge shows "unknown"
- No coverage data in artifacts

**Solution:**

1. Check JaCoCo generates report:
   ```bash
   ./mvnw clean verify jacoco:report
   ls -l target/site/jacoco/
   ```

2. If report missing, add to pom.xml (already done)

3. Check Codecov secrets:
   - Go to repository Settings → Secrets
   - Verify `CODECOV_TOKEN` exists (if not added)
   - Regenerate token if expired

### Problem 4: Release Fails to Create

**Symptoms:**
- Pushed tag v1.0.0
- Release workflow not triggered

**Debug steps:**

```bash
# Verify tag format
git tag -l
# Should show: v1.0.0, v1.0.1, etc. (starts with 'v')

# Verify tag was pushed
git push origin v1.0.0

# Check tag exists on GitHub
git ls-remote --tags origin

# If still not working, check GitHub Token permissions:
# Settings → Developer settings → Personal access tokens
# Ensure "repo" scope is selected
```

### Problem 5: Slow Builds (> 15 minutes)

**Symptoms:**
- CI takes 15+ minutes to complete
- Timeout approaching

**Optimization:**

1. **Enable Maven cache** (already in ci.yml):
   ```yaml
   cache: maven
   ```

2. **Skip tests during build check** (if needed):
   ```bash
   ./mvnw clean package -DskipTests
   ```

3. **Run tests in parallel**:
   ```yaml
   - name: Run tests in parallel
     run: ./mvnw test -T 1C  # 1 core per CPU
   ```

4. **Use matrix for parallel jobs**:
   ```yaml
   strategy:
     matrix:
       test-suite: [unit, integration]
   ```

---

## Part 7: Monitoring & Maintenance

### Regular Checks

**Weekly:**
- Click Actions tab to verify builds are passing
- Check build times to track performance trends
- Review any failed workflows and fix issues

**Monthly:**
- Download and review code coverage reports
- Check SonarCloud for new code smells
- Review dependency check reports for vulnerabilities

### Getting Notifications

**GitHub Notifications:**

1. Go to: Settings → Notifications
2. Select "Participating and @mentions"
3. You'll get email when:
   - Workflows fail
   - PRs need action
   - Releases created

**Optional: Slack Integration**

Set up Slack notifications for workflow status:

1. Go to: `https://github.com/apps/github-slack-integration`
2. Install GitHub Slack app
3. Follow authentication steps
4. Subscribe to channel for workflow notifications

### Cleaning Up Artifacts

Artifacts are stored under retention-days limit:

```yaml
- uses: actions/upload-artifact@v3
  with:
    retention-days: 90  # Keep 90 days, then auto-delete
```

To manually delete:

1. Go to: Settings → Actions → General
2. Click "Remove all" under "Artifacts and logs"

---

## Part 8: Quick Command Reference

### Common Git Commands

```bash
# Create feature branch
git checkout -b feature/name

# Make changes and commit
git add .
git commit -m "Description"

# Push to GitHub
git push origin feature/name

# Create tag for release
git tag -a v1.0.0 -m "Release message"
git push origin v1.0.0

# Delete local tag
git tag -d v1.0.0

# Delete remote tag
git push origin --delete v1.0.0
```

### Common GitHub CLI Commands

```bash
# Install: https://cli.github.com/

# Login
gh auth login

# View workflow runs
gh run list --workflow=ci.yml

# View specific run details
gh run view 12345 --log

# Cancel run
gh run cancel 12345

# Download artifacts
gh run download 12345 --name test-results

# Create release
gh release create v1.0.0 --title "Release 1.0.0" --notes "Changes..."

# View releases
gh release list
```

---

## Summary

**Your CI/CD Pipeline is Now Ready!**

| Workflow | When It Runs | What It Does |
|----------|-------------|------------|
| **CI Pipeline** | Every push & PR | Builds, tests, generates coverage |
| **Code Quality** | Every push & PR | Analyzes code, scans vulnerabilities |
| **Release** | Git tag push | Creates release, builds Docker image |

**Next Steps:**

1. ✓ Push code to GitHub
2. ✓ Go to Actions tab to monitor builds
3. ✓ Create pull requests to test CI
4. ✓ Tag commit with `v1.0.0` to create release
5. ✓ Monitor workflow status regularly

**Helpful Resources:**

- GitHub Actions Docs: https://docs.github.com/en/actions
- Soft School Documentation: See README.md and TESTING_GUIDE.md
- SonarCloud: https://sonarcloud.io/dashboard
- GitHub CLI: https://cli.github.com/

**Support:** Check workflow logs for detailed error messages. Comment in issues if problems persist.

