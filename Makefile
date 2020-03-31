.PHONE: build clean describe run

app=vrp
version=0.1
descriptor=jar-with-dependencies
app-with-all-deps=$(app)-$(version)-$(descriptor).jar
resource=resources/defaultVrpDataFile.txt

build:
	mvn clean install && mkdir -p build && cp target/$(app-with-all-deps) build

clean:
	@echo Removing build/ and target/ directories && rm -rf build/ && rm -rf target/

describe:
	@echo Application name containg all dependencies is: $(app-with-all-deps)

run:
	@echo Running application with parameter:
	java -jar build/$(app-with-all-deps) $(resource)