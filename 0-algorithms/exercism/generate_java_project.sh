#!/bin/bash

# Prompt the user for the artifact ID
read -p "Enter the artifact ID: " ARTIFACT_ID

# Check if the user entered a value
if [ -z "$ARTIFACT_ID" ]; then
  echo "Artifact ID cannot be empty. Exiting."
  exit 1
fi

# Replace spaces with hyphens
ARTIFACT_ID=$(echo "$ARTIFACT_ID" | tr ' ' '-')

# Generate the Maven project using the modified artifact ID
mvn archetype:generate \
  -DgroupId=com.yourcompany.app \
  -DartifactId="$ARTIFACT_ID" \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false

echo "Maven project '$ARTIFACT_ID' has been generated."

