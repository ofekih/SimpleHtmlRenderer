#!/bin/sh
rm -rf docs/
javadoc -author -d docs/public src/*.java
javadoc -author -private -d docs/private src/*.java