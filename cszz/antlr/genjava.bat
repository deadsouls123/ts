@echo off
java -cp %~dp0/lib/antlr-4.5.1-complete.jar org.antlr.v4.Tool %* CszzLexer.g4 -o build
java -cp %~dp0/lib/antlr-4.5.1-complete.jar org.antlr.v4.Tool %* CszzParser.g4 -o build -lib build
