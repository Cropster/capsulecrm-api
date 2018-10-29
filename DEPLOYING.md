# Setup

Create  `~/.gradle/gradle.properties`

```
nexusUser=
nexusPass=
```

# Build

```
./gradlew clean build
```


# Publish local

Puts the jar in ~/.m2

```
./gradlew publishToMavenLocal
```


# Publish version

```
./gradlew upload
```

