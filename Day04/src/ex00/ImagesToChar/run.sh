#!/bin/bash

# Определяем путь к папке target
TARGET_DIR="target"

# Проверяем, существует ли папка target
if [ ! -d "$TARGET_DIR" ]; then
  echo "Папка $TARGET_DIR не существует. Создаем..."
  mkdir -p "$TARGET_DIR"
fi

javac -d target src/java/edu/school21/printer/app/MainApp.java src/java/edu/school21/printer/logic/*.java

java -cp target edu.school21.printer.app.MainApp --white=. --black=O --pathToImage=/Users/corinnem/Java/Java_Bootcamp.Day04-2/src/ex00/ImagesToChar/recource/it.bmp