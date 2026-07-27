# Upgrade Plan: Inventory_management_system (20260725200727)

- **Generated**: 2026-07-26 01:38
- **HEAD Branch**: main
- **HEAD Commit ID**: N/A

## Available Tools

**JDKs**
- JDK 17.0.19: C:\Users\Admin\AppData\Roaming\Code\User\globalStorage\pleiades.java-extension-pack-jdk\java\17\bin (current project JDK)
- JDK 21.0.11: C:\Users\Admin\AppData\Roaming\Code\User\globalStorage\pleiades.java-extension-pack-jdk\java\21\bin
- JDK 25.0.3: C:\Program Files\Eclipse Adoptium\jdk-25.0.3.9-hotspot\bin (target runtime)

**Build Tools**
- Maven 3.9.16: C:\Program Files\apache-maven-3.9.16\bin

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: appmod/java-upgrade-20260725200727
- Run tests before and after the upgrade: true

## Upgrade Goals

- Java 25 LTS

## Technology Stack

| Technology/Dependency | Current | Min Compatible Version | Why Incompatible |
| ---------------------- | ------- | ---------------------- | ---------------- |
| Java | 17 | 25 | User requested |
| Maven Compiler | 17 | 25 | Target runtime upgrade |

## Derived Upgrades

- Upgrade Maven compiler source/target from 17 to 25 to align the project with the Java 25 runtime.

## Impact Analysis

### Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
|------|-----------|---------|--------|--------|--------|
| backend/pom.xml | maven.compiler.source | 17 | upgrade | 25 | User requested Java 25 LTS target |
| backend/pom.xml | maven.compiler.target | 17 | upgrade | 25 | User requested Java 25 LTS target |

### Source Code Changes

| File | Location | Current | Required Change | Reason |
|------|----------|---------|----------------|--------|
| backend/src/main/java/com/aditya/Main.java | none | Java code is already source-compatible | No code changes required | Existing code is simple and compatible |

### Configuration Changes

| File | Property/Setting | Current | Required Change | Reason |
|------|------------------|---------|----------------|--------|
| backend/pom.xml | compiler properties | 17 | update to 25 | Align Maven compilation with Java 25 |

### Risks & Warnings

- The project is a very small Maven application, so the main upgrade risk is build-tool configuration rather than source incompatibility.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Ensure the target Java runtime is available before changing the build configuration.
  - **Changes to Make**: Use the installed Java 25 runtime for verification.
  - **Verification**: Run `java -version` and confirm Java 25 is available.

- Step 2: Setup Baseline
  - **Rationale**: Capture the current build state before applying the runtime upgrade.
  - **Changes to Make**: Run the existing Maven build once with the current configuration.
  - **Verification**: Run `mvn -q -DskipTests compile` from the backend module.

- Step 3: Upgrade Java Target to 25
  - **Rationale**: Update the Maven compiler settings to target the new Java LTS runtime.
  - **Changes to Make**: Apply the dependency/configuration changes from Impact Analysis in backend/pom.xml.
  - **Verification**: Run `mvn -q -DskipTests compile` and `mvn -q test` with Java 25.

- Step 4: Final Validation
  - **Rationale**: Confirm the project builds and tests successfully under Java 25.
  - **Changes to Make**: None beyond any fixes needed from build/test feedback.
  - **Verification**: Run `mvn -q test` with Java 25.
