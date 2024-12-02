Инструкции написаны для состояния, когда консоль открыта в корневой папке проекта:

run:
    sh run.sh

Описание:
    Компиляция: javac -d target src/java/edu/school21/printer/app/MainApp.java src/java/edu/school21/printer/logic/*.java
    Создание JAR-файла: jar cvfm target/images-to-chars-printer.jar src/MANIFEST.MF -C target . -C src/resources .
    Запуск: java -jar target/images-to-chars-printer.jar --white=. --black=O