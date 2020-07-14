package org.mitre.sample;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.hl7.fhir.utilities.cache.ToolsVersion;
import org.hl7.fhir.validation.ValidationEngine;
import org.hl7.fhir.utilities.cache.FilesystemPackageCacheManager;

public class Main {

  public static void main(String[] args) throws Exception {
    // Set up validator and package cache manager
    ValidationEngine validator = new ValidationEngine("hl7.fhir.r4.core#4.0.1");
    FilesystemPackageCacheManager pcm =
        new FilesystemPackageCacheManager(true, ToolsVersion.TOOLS_VERSION);

    // Get known package IDs
    Map<String, String> igs = new HashMap<>();
    pcm.listAllIds(igs);

    // Try loading each ID into the validator, logging IDs that don't work to a file
    try (FileWriter fw = new FileWriter("incompatible-tx.csv");
         PrintWriter pw = new PrintWriter(fw, true)) {
      pw.println("package ID,exception,message");
      Set<String> ids = igs.keySet();
      String size = String.valueOf(igs.size());
      int counter = 0;
      for (String id : ids) {
        counter++;
        try {
          validator.loadIg(id, true);
          System.out.format("[%" + size.length() + "d/%s] PASS (%s)%n", counter, size, id);
        } catch (Exception e) {
          String exception = e.getClass().getCanonicalName();
          String message = e.getMessage().replace(",", "");
          pw.format("%s,%s,%s%n", id, exception, message);
          System.out.format("[%" + size.length() + "d/%s] FAIL (%s)%n", counter, size, id);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("Failed to open file for writing.");
      System.exit(1);
    }
  }
}
