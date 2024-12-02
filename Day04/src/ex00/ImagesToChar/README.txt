Инструкции написаны для состояния, когда консоль открыта в корневой папке проекта:

run:
    sh run.sh

Описание:
    Компиляция: javac -d target src/java/edu/school21/printer/app/MainApp.java src/java/edu/school21/printer/logic/*.java
    Запуск: java -cp target edu.school21.printer.app.MainApp --white=. --black=O --pathToImage=/Users/corinnem/Java/Java_Bootcamp.Day04-2/src/ex00/ImagesToChar/recource/it.bmp