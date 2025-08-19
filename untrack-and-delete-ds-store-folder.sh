#!/bin/bash

# Find and untrack all .DS_Store directories
git rm -r --cached `find . -type d -name ".DS_Store"`

# Delete the .DS_Store directories
find . -type d -name ".DS_Store" -exec rm -rf {} +

# Add .DS_Store to .gitignore if it's not already there
if ! grep -q ".DS_Store/" .gitignore; then
    echo ".DS_Store/" >> .gitignore
fi

# Commit changes
git commit -m "Stop tracking and delete .DS_Store directories"

