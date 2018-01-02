#!/bin/bash
git config --global user.name "Xavier Ramirez"
git config --global user.email "xavier@dreamcode.io"
LAST_MESSAGE="$(git log --oneline -1)"
if [[ $LAST_MESSAGE == *"Setting version to "* ]]; then
  exit 1
fi
sbt 'release with-defaults'
git checkout develop
git pull
git merge master
git push origin develop
