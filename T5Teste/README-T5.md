# Compiladores - T5: Analisador Semântico  

- Felipe Yuya Sato RA: 802444  
- Rafael Martins Cavalheiro Andrade RA: 800446

### Compilação  

Tendo clonado esse repositório, vá até o diretório do programa e compile com Maven:

```
mvn clean package
```

### Execução  

Compile com java passando dois argumentos

1. O caminho literal até o arquivo com o código em LA
2. O caminho literal até o arquivo onde a saída será escrita

```
java -jar target/alguma-t5-1.0-SNAPSHOT-jar-with-dependencies.jar ~/path/to/input/file.txt ~/path/to/output/file.txt
```

### Corretor  

Para rodar o corretor disponível em <https://github.com/dlucredio/compiladores-corretor-automatico/> use este comando na pasta do programa corretor:

```
java -jar target/compiladores-corretor-automatico-1.0-SNAPSHOT-jar-with-dependencies.jar "java -jar ~/{path/to/parser}/alguma-t5/target/alguma-t5-1.0-SNAPSHOT-jar-with-dependencies.jar" gcc ~/temp ~//TestFiles/casos-de-teste/casos-de-teste/ "RA" t5 
```
