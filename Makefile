JUNIT_JAR = lib/junit-4.13.2.jar
HAMCREST_JAR = lib/hamcrest-core-1.3.jar
CLASSPATH = build:$(JUNIT_JAR):$(HAMCREST_JAR)
SOURCE_FILES != find src -name '*.java'
TEST_FILES != find test -name '*.java'
run: compile
	java -cp $(CLASSPATH) Main $(subst build/,,$(subst .class,,$(wildcard build/*.class)))

test: compile
	java -cp $(CLASSPATH) org.junit.runner.JUnitCore $(subst build/,,$(subst .class,,$(wildcard build/*Test.class)))

test-auto: compile
	find . -name '*.java' | entr make test

compile:
	javac -cp $(CLASSPATH) $(SOURCE_FILES) $(TEST_FILES) -d build


clean:
	rm -f build/*.class
