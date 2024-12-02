#!/bin/bash

# Определяем путь к папке target
TARGET_DIR="target"

# Проверяем, существует ли папка target
if [ ! -d "$TARGET_DIR" ]; then
  echo "Папка $TARGET_DIR не существует. Создаем..."
  mkdir -p "$TARGET_DIR"
else
  rm -r target
  mkdir -p "$TARGET_DIR"
fi

find src -name "*.java" > sources.txt

#cvfm:
#c — создать новый JAR-файл.
#v — выводить информацию о процессе создания (verbose).
#f — указать имя JAR-файла.
#m — указать путь к файлу манифеста MANIFEST.MF.

javac -cp "lib/jcommander-1.82.jar:lib/JCDP-4.0.2.jar" -d target @sources.txt
jar cvfm target/images-to-chars-printer.jar src/MANIFEST.MF -C target . -C src/resources .
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN
cp -r src/resources target

# Распаковка JAR и копирование классов
for jar in lib/*.jar; do
    jar xf "$jar"
    cp -r com target/
done

# Удаление распакованных файлов
rm -r com META-INF
