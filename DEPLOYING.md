# Setup

Create  `~/.gradle/gradle.properties`

```
cropsterNexusUser=
cropsterNexusPass=
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

