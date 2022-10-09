CC = clang
CFLAGS = -Wall -Wextra -Werror -Wno-newline-eof -Wno-unused-parameter -pedantic -std=c99 -ferror-limit=1
LDFLAGS = -lm
DIFF = diff
# -y diffs side by side.

PROGRAM = program
SOURCE_FILES = program.c
HEADER_FILES = $(shell find . -type f -name '*.h')
OUTPUT_FILES = $(shell find . -type f -name '*myoutput.txt')
OBJECT_FILES = $(SOURCE_FILES:.c=.o)

.PHONY: all build clean run test0 run0 testall

all: build

testall: test0 test1

build: $(PROGRAM)

clean:
	rm -f $(PROGRAM) $(OBJECT_FILES) $(OUTPUT_FILES)

run: run0

test0: build
	./$(PROGRAM) < test0.txt > test0-myoutput.txt
	$(DIFF) test0-myoutput.txt test0-output.txt

run0: build
	./$(PROGRAM) < test0.txt

test1: build
	./$(PROGRAM) < test1.txt > test1-myoutput.txt
	$(DIFF) test1-myoutput.txt test1-output.txt

run1: build
	./$(PROGRAM) < test1.txt

$(PROGRAM): $(OBJECT_FILES)
	$(CC) -o $@ $^ $(LDFLAGS)