# Makefile for compiling Java files with dependencies

# Répertoire des fichiers source
SRC_DIR = src

clean:
	@find $(SRC_DIR) -name "*.class" -exec rm -f {} \;
