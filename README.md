## Running the Java Program
To run the Java program, run `./gradlew run` from the project root directory.
This will generate a CSV file will all of the package IDs that failed to be
loaded into the validator, along with the name and message of the exception that
was thrown.

## Running the Bash Script
This will only work **after you've run the Java program**, as it requires the
CSV file generated from the Java program.

1. [Download](https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator#UsingtheFHIRValidator-Downloadingthevalidator)
the version of the Desktop Jar that you want to test with.
2. Move the downloaded Jar to the root directory of this project.
3. Edit the `VALIDATOR_JAR` variable in the `check_igs.sh` script to match the
   name of the downloaded Jar. Also ensure that the `FILE` variable matches the
   file name of the generated CSV file. Change any of the other variables to
   your liking.
