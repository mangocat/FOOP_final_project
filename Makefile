all:
	rm -r out/
	javac -sourcepath src/ -d out/ src/*.java
run:
	java -cp out/ Main
