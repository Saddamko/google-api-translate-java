/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.google.api.translate;

// [START translate_v3_translate_text_0]
// Imports the Google Cloud Translation library.
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslateTextResponse;
import com.google.cloud.translate.v3.Translation;
import com.google.cloud.translate.v3.TranslationServiceClient;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// [END translate_v3_translate_text_0]

public class TranslateText {

    public static String[][] data = new String [60000][3];
    public static String[][] resizedArray; 
    public static int arraySize=0;
  // [START translate_v3_translate_text_1]
  // Set and pass variables to overloaded translateText() method for translation.
  public static void translateText() throws IOException {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "analog-analyzer-427521-p4"; //YOUR-PROJECT-ID";
    // Supported Languages: https://cloud.google.com/translate/docs/languages
    String targetLanguage = "uk";
    String text = "Process Checkpoint Response";
    translateText(projectId, targetLanguage, text);
  }
  // [END translate_v3_translate_text_1]

  // [START translate_v3_translate_text_2]
  // Translate text to target language.
  public static String translateText(String projectId, String targetLanguage, String text)
      throws IOException {

    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (TranslationServiceClient client = TranslationServiceClient.create()) {
      // Supported Locations: `global`, [glossary location], or [model location]
      // Glossaries must be hosted in `us-central1`
      // Custom Models must use the same location as your model. (us-central1)
      LocationName parent = LocationName.of(projectId, "global");

      // Supported Mime Types: https://cloud.google.com/translate/docs/supported-formats
      TranslateTextRequest request =
          TranslateTextRequest.newBuilder()
              .setParent(parent.toString())
              .setMimeType("text/plain")
              .setTargetLanguageCode(targetLanguage)
              .addContents(text)
              .build();

      TranslateTextResponse response = client.translateText(request);
      
      String transText=null;

      // Display the translation for each input text provided
      for (Translation translation : response.getTranslationsList()) {
          transText=translation.getTranslatedText();
//        System.out.printf("Translated text: %s\n", translation.getTranslatedText());
      }
      return transText;
    }
  } 
  // [END translate_v3_translate_text_2]
  
   public static void readCSV () {
        String csvFile = "C:\\TEMP\\S_LST_OF_VAL_ENU.csv";
        String line;
        String csvSplitBy = ",";
        int i=0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "UTF-8"))) {
            while ((line = br.readLine()) != null ) {
                // use comma as separator
                String[] values = line.split(csvSplitBy);
                // Process the values
                String projectId = "analog-analyzer-427521-p4"; //YOUR-PROJECT-ID";
                // Supported Languages: https://cloud.google.com/translate/docs/languages
                String targetLanguage = "uk";
                String text = values[3];
                String translatedText = translateText(projectId, targetLanguage, text);
                
               data[i][0]=values[1];
               data[i][1]=values[2];
               data[i][2]=translatedText;
//                for (String value : values) {
//                    System.out.print(value + " ");
//                }
//                System.out.println();
                arraySize=i;
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
   public static void reduceArray()
   {
        resizedArray = new String[arraySize][3];

        // Copy elements from the original array to the new array
        System.arraycopy(data, 0, resizedArray, 0, arraySize);
   }
   
    public static void writeCSV(String[][] data) {
//        String csvFile = "C:\\temp\\MY_S_SYM_STR_INTL_UKR.csv";
//        String[] header = { "LANG_CD", "SYM_STR_KEY", "STRING_VALUE" };
        String csvFile = "C:\\temp\\MY_S_LST_OF_VAL_UKR.csv";
        String[] header = { "NAME", "TYPE", "VAL" };        

//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"))) {
            // Write header
            for (int i = 0; i < header.length; i++) {
                bw.write(header[i]);
                if (i < header.length - 1) {
                    bw.write(",");
                }
            }
            bw.newLine();

            // Write data
            for (String[] row : resizedArray) {
                for (int i = 0; i < row.length; i++) {
                    bw.write(row[i]);
                    if (i < row.length - 1) {
                        bw.write(",");
                    }
                }
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
  public static void main(String args[]) {
      try {
          readCSV();
          reduceArray();
          writeCSV(data);
          if (false) translateText();
      } catch (IOException ex) {
          Logger.getLogger(TranslateText.class.getName()).log(Level.SEVERE, null, ex);
      }
  }

}