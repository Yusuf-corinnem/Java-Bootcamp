Инструкции написаны для состояния, когда консоль открыта в корневой папке проекта:

run:
    sh run.sh

Включенные внешние библиотеки:
    - jcommander-1.82.jar
    - JCDP-4.0.2.jar

Описание:
    Компиляция: javac -cp "lib/jcommander-1.82.jar:lib/JCDP-4.0.2.jar" -d target @sources.txt
    Создание JAR-файла: jar cvfm target/images-to-chars-printer.jar src/MANIFEST.MF -C target . -C src/resources .
    Запуск: java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN