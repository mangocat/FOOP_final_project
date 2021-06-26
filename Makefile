all:
	if [ -d "out/" ]; then rm -rf out/; fi
	javac -sourcepath src/ -d out/ src/*.java
run:
	java -cp out/ Main
