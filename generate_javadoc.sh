rm javadoc/*
javadoc -author -d javadoc *.java
rm javadoc_private/*
javadoc -author -private -d javadoc_private *.java