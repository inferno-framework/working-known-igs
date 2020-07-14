## About
This project goes through each known IG package ID (gathered using
`FilesystemPackageCacheManager::listIds`) and determines which IDs cause the
validator to throw an exception when loaded.

## Running the Java Program
To run the Java program, run `./gradlew run` from the project root directory.
This will generate a CSV file will all of the package IDs that failed to be
loaded into the validator, along with the name and message of the exception that
was thrown. You can find `incompatible.csv` as an example of what the file
that's generated will look like.

## Running the Bash Script
This will only work **after you've run the Java program**, as it requires the
CSV file generated from the Java program. This script loops through all of the
package IDs in the generated CSV that failed to be loaded, logging each ID to an
output file if they also fail when loaded into the Desktop Jar. You can find
`desktop_incompatible.txt` as an example of what the output file.

1. [Download](https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator#UsingtheFHIRValidator-Downloadingthevalidator)
the version of the Desktop Jar that you want to test with.
2. Move the downloaded Jar to the root directory of this project.
3. Edit the `VALIDATOR_JAR` variable in the `check_igs.sh` script to match the
   name of the downloaded Jar. Also ensure that the `FILE` variable matches the
   file name of the generated CSV file. Change any of the other variables to
   your liking.

## Comparing Results
If you run the command
```
comm <(awk -F',' 'NR > 1 {print $1}' incompatible.csv | sort) <(sort desktop_incompatible_no_version.txt)
```
you will find that out of the 198 IGs that failed to be loaded into the
validator in the Java program, only `hl7.fhir.core` is successfully loaded into
the Desktop Jar.

## License

Copyright 2020 The MITRE Corporation

Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at
```
http://www.apache.org/licenses/LICENSE-2.0
```
Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
