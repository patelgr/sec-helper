#!/bin/zsh
current_dir=$(basename "$PWD")
echo "current path: $(pwd)"
echo "current module: $current_dir"
read -p  "Continue (y/n)?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
  mkdir -p src/main/java
  mkdir -p src/main/resources
  mkdir -p src/test/java
  mkdir -p src/test/resources
else
  echo "Exiting:";
fi
