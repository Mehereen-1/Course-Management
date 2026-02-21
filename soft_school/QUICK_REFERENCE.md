# 📌 Quick Reference - GitHub Actions & Development Guide

**Bookmark this page for quick lookup of common commands and scenarios.**

---

## 🎯 Daily Developer Tasks

### Starting Work on a New Feature

```bash
# 1. Update main branch
git checkout main
git pull origin main

# 2. Create feature branch
git checkout -b feature/my-feature-name

# 3. Make changes and commit
echo "Your code here" > src/main/java/com/example/NewFile.java
git add .
git commit -m "Add my feature"

# 4. Push to GitHub
git push origin feature/my-feature-name

# 5. Go to GitHub → Create Pull Request
# Wait for CI to pass ✓
```

### Running Tests Locally

```bash
# Run all unit tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=UserServiceTest

# Run with coverage
./mvnw clean test jacoco:report

# View coverage report
open target/site/jacoco/index.html  # macOS
start target/site/jacoco/index.html # Windows
```

### Building and Running Locally

```bash
# Clean build
./mvnw clean install

# Run application
./mvnw spring-boot:run

# Build Docker image
docker build -t softschool:local .

# Run with Docker Compose
docker-compose up
```

---

## 🔄 GitHub Actions Workflows

### CI Pipeline Status

```bash
# View recent CI runs
gh run list --workflow=ci.yml --limit 10

# View specific run details
gh run view <run-id>

# View logs for a run
gh run view <run-id> --log

# Download test artifacts
gh run download <run-id> --name test-results

# Cancel a running workflow
gh run cancel <run-id>
```

### Creating Releases

```bash
# 1. Tag the commit (replaces vX.Y.Z with your version)
git tag -a v1.0.0 -m "Release version 1.0.0"

# 2. Push tag to GitHub
git push origin v1.0.0

# 3. View release (GitHub creates it automatically)
# Go to: https://github.com/YOUR_ORG/soft-school/releases

# If you need to delete the tag:
git tag -d v1.0.0              # Delete locally
git push origin --delete v1.0.0 # Delete on GitHub
```

---

## 📊 Monitoring Builds

### Quick Status Check

1. Go to: **https://github.com/YOUR_ORG/soft-school/actions**
2. Look at latest workflow run
3. Check for ✓ (passed) or ✗ (failed)

### Detailed Build Logs

```
Actions tab → Click workflow run → Click "Build and Test" → Scroll through logs
```

### Viewing Test Results

**Download artifacts:**

```bash
# List available artifacts
gh run download <run-id> --dir ./artifacts

# Or manually:
1. Actions → Latest run
2. Scroll to Artifacts section
3. Download "test-results" ZIP
```

**View code coverage:**

```bash
# Download coverage report
gh run download <run-id> --name code-coverage-report --dir ./

# Extract and open
unzip code-coverage-report.zip
open target/site/jacoco/index.html
```

---

## 🛠️ Troubleshooting

### Build Failed - What to Do?

```
1. Check logs: Actions → Failed workflow → Click step with ✗
2. Common issues:
   - Compilation error → Fix code, push again
   - Test failure → Run ./mvnw test locally
   - Database issue → Check PostgreSQL service in logs
3. Still stuck? Check GITHUB_ACTIONS_GUIDE.md troubleshooting section
```

### Tests Fail in CI But Pass Locally

```bash
# Try running locally with same environment:
./mvnw clean verify

# If that fails:
- Check if PostgreSQL is running locally
- Check database credentials match
- Look for OS-specific issues (case sensitivity)

# If local passes but CI fails:
- Likely timing issue with database
- Solution: Increase health check timeout in ci.yml
```

### Workflow Not Triggering

```bash
# For CI pipeline:
- Should trigger on push to main/develop
- Check: Did you push (not just commit)?
- git push origin main

# For release workflow:
- Only triggered by tags starting with 'v'
- Correct: git tag -a v1.0.0 ...
- Wrong: git tag -a release-1.0.0 ...
```

---

## 📚 Documentation Files

| File | Purpose |
|------|---------|
| **README.md** | Project overview and setup |
| **TESTING_GUIDE.md** | Detailed testing documentation |
| **GITHUB_ACTIONS_GUIDE.md** | Comprehensive GitHub Actions reference |
| **GITHUB_ACTIONS_SETUP.md** | Step-by-step setup walkthrough |
| **USAGE_GUIDE.md** | Application usage guide |
| **HELP.md** | Project help and FAQs |

---

## 🔐 Configuration Checklists

### Initial Setup (One-time)

- [ ] Code pushed to GitHub
- [ ] Workflows visible in Actions tab
- [ ] First CI run passed (or check logs if failed)
- [ ] (Optional) SonarQube secrets configured
- [ ] (Optional) Docker Hub credentials set up

### Before Creating Release

- [ ] All tests passing locally
- [ ] Code committed and pushed to main
- [ ] Version number decided (e.g., v1.0.0)
- [ ] Release notes prepared

### Release Checklist

```bash
# 1. Verify you're on main and up-to-date
git checkout main
git pull origin main

# 2. Double-check tests pass
./mvnw clean test

# 3. Create and push tag
git tag -a v1.0.0 -m "Release version 1.0.0 - Production ready"
git push origin v1.0.0

# 4. Monitor release workflow
# Go to Actions → Release and Deploy workflow

# 5. Once released, PR new develop updates to main
git checkout develop
git commit -m "Bump version to v1.0.1-SNAPSHOT"
git push origin develop
git checkout main
# Create PR on GitHub
```

---

## 🌐 Useful Links

**Local Development:**
- Application: http://localhost:8080
- Docker Compose: `docker-compose up`

**GitHub:**
- Repository: https://github.com/YOUR_ORG/soft-school
- Actions: https://github.com/YOUR_ORG/soft-school/actions
- Releases: https://github.com/YOUR_ORG/soft-school/releases

**External Services (Optional):**
- SonarCloud Dashboard: https://sonarcloud.io/dashboard
- SonarCloud Tokens: https://sonarcloud.io/account/security/
- Codecov: https://codecov.io

**Tools:**
- GitHub CLI Installation: https://cli.github.com/
- Maven: https://maven.apache.org/
- Java 17 Download: https://adoptium.net/

---

## 💡 Pro Tips

### Speed Up Local Builds

```bash
# Skip tests for faster building
./mvnw clean install -DskipTests

# Or skip both tests and javadoc
./mvnw clean install -DskipTests -Dspeed
```

### Use GitHub CLI Aliases

```bash
# Add these to ~/.bashrc or ~/.zshrc

# View latest workflow
alias gh-log='gh run list --workflow=ci.yml --limit 1'

# Quick test results download
alias gh-test='gh run download $(gh run list --workflow=ci.yml --limit 1 | head -1 | awk "{print \$1}") --name test-results'

# Then use:
gh-log
gh-test
```

### Auto-format Code Before Committing

```bash
# Install formatter plugin if not present
./mvnw fmt:format

# Then commit
git add .
git commit -m "Format code"
```

### View Workflow Secrets (Without Values)

```bash
# Lists secret names (not values - safe to share)
gh secret list --repo YOUR_ORG/soft-school
```

---

## 📞 Getting Help

**For specific errors:** Check the error message in workflow logs first.

**Common resources:**
1. GITHUB_ACTIONS_TROUBLESHOOTING section in GITHUB_ACTIONS_GUIDE.md
2. This quick reference document
3. Project HELP.md file

**Community:**
- GitHub Actions: https://docs.github.com/en/actions
- Spring Boot: https://spring.io/projects/spring-boot
- Maven: https://maven.apache.org/guides/

---

## 📝 Command Cheat Sheet

| Task | Command |
|------|---------|
| **Create branch** | `git checkout -b feature/name` |
| **Commit code** | `git add . && git commit -m "Message"` |
| **Push code** | `git push origin feature/name` |
| **Create tag** | `git tag -a v1.0.0 -m "Message"` |
| **Push tag** | `git push origin v1.0.0` |
| **Run tests** | `./mvnw test` |
| **Build project** | `./mvnw clean install` |
| **Check build status** | `gh run list --workflow=ci.yml` |
| **View logs** | `gh run view <id> --log` |
| **Download artifacts** | `gh run download <id> --name test-results` |
| **List secrets** | `gh secret list` |

---

**Last Updated:** 2024  
**Version:** 1.0  
**Maintainers:** Development Team

For the complete guide, see [GITHUB_ACTIONS_GUIDE.md](GITHUB_ACTIONS_GUIDE.md)
