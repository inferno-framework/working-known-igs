#!/usr/bin/env bash

FILE=incompatible.csv
VALIDATOR_JAR=org.hl7.fhir.validation.cli-5.0.13-20200707.202332-3.jar
INPUT=patient.json
OUTPUT=desktop_incompatible.txt

cat /dev/null > "$OUTPUT"

cat "$FILE" | awk -F',' 'NR > 1 {print $1}' |
  while read -r id; do
    java -jar "$VALIDATOR_JAR" -ig "$id" "$INPUT"
    exit_status=$?
    if [ $exit_status -eq 130 ]; then
      exit 1
    elif [ $exit_status -ne 0 ]; then
      echo "$id" >> "$OUTPUT"
    fi
  done
