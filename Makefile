JUNIT_JAR = lib/junit-4.13.2.jar
HAMCREST_JAR = lib/hamcrest-core-1.3.jar
CLASSPATH = src:test:$(JUNIT_JAR):$(HAMCREST_JAR)
SOURCE_FILES = $(wildcard src/*.java)
TEST_FILES = $(wildcard test/*.java)

run: compile
	java -cp $(CLASSPATH) Main $(subst /,.,$(subst test/,,$(TEST_FILES:.java=)))

test: compile
	java -cp $(CLASSPATH) org.junit.runner.JUnitCore $(subst /,.,$(subst test/,,$(TEST_FILES:.java=)))

test-auto: compile
	find . -name '*.java' | entr make test

compile:
	javac -cp $(CLASSPATH) $(SOURCE_FILES) $(TEST_FILES)


clean:
	rm -f src/*.class test/*.class
