#!/bin/bash

# Step 1: Run git add .
echo "Adding changes to the staging area..."
git add .

# Step 2: Prompt the user for a commit message
read -p "Enter commit message: " prompt

# Step 3: Use the prompt as the commit message
echo "Committing changes..."
git commit -m "$prompt"

# Step 4: Push changes to the remote repository
echo "Pushing changes to the remote repository..."
git push

echo "Operation completed successfully."

