default:
	@echo 'Targets:'
	@echo '  run'
	@echo '  build'
	@echo '  clean'

run: build
	@java Main

build:
	@javac Main.java

clean:
	-rm -f *.class
	-rm -f astro/practical/containers/*.class
	-rm -f astro/practical/lib/*.class
	-rm -f astro/practical/test/*.class
