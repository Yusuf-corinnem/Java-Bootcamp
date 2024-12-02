#!/bin/bash

# Определяем путь к папке target
TARGET_DIR="target"

# Проверяем, существует ли папка target
if [ ! -d "$TARGET_DIR" ]; then
  echo "Папка $TARGET_DIR не существует. Создаем..."
  mkdir -p "$TARGET_DIR"
fi

#cvfm:
#c — создать новый JAR-файл.
#v — выводить информацию о процессе создания (verbose).
#f — указать имя JAR-файла.
#m — указать путь к файлу манифеста MANIFEST.MF.

javac -d target src/java/edu/school21/printer/app/MainApp.java src/java/edu/school21/printer/logic/*.java
jar cvfm target/images-to-chars-printer.jar src/MANIFEST.MF -C target . -C src/resources .
java -jar target/images-to-chars-printer.jar --white=. --black=O
cp -r src/resources target