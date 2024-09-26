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
	-rm -f astro/practical/data/*.class
	-rm -f astro/practical/lib/*.class
	-rm -f astro/practical/models/*.class
	-rm -f astro/practical/models/data/*.class
	-rm -f astro/practical/test/*.class
	-rm -f astro/practical/types/*.class
	-rm -f astro/practical/types/complex/*.class
