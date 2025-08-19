#!/bin/bash

# Check if we are in a Git repository
if [ ! -d .git ]; then
    echo "This script must be run in a Git repository."
    exit 1
fi

# Find and untrack all .DS_Store files and directories
echo "Untracking .DS_Store files and directories..."
git rm -r --cached $(find . -name ".DS_Store" -print)

# Delete the .DS_Store files and directories
echo "Deleting .DS_Store files and directories..."
find . -name ".DS_Store" -exec rm -rf {} +

# Add .DS_Store to .gitignore if it's not already there
if ! grep -q ".DS_Store" .gitignore; then
    echo ".DS_Store" >> .gitignore
    echo ".DS_Store added to .gitignore."
fi

# Stage all the changes to the repository
git add .

# Commit the changes
git commit -m "Stop tracking and delete .DS_Store files and directories"

echo "Operation completed ✅"
