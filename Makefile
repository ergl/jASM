.PHONY: compile clean

all: compile

compile:
	mvn package

clean:
	mvn clean

