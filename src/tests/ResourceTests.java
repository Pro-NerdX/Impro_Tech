package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class ResourceTests {
    
    @Test
    public void formatGermanTest() {
        final File file = new File("src/resources/exercisesGerman.md");
        try {
            final Scanner scanner = new Scanner(file);
            int lineCounter = 0;
            boolean expectMotivation = false;
            boolean expectExercise = false;
            boolean expectExplanation = false;
            boolean expectRemarks = false;
            int numberOfExercisesFromLastTopic = 0;
            boolean wasFirstTopic = true;
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (line.startsWith("# ")) {            // new topic found
                    if (!wasFirstTopic) {
                        if (numberOfExercisesFromLastTopic == 0) {
                            System.out.println("No exercises above line: " + lineCounter);
                        }
                        assert (numberOfExercisesFromLastTopic != 0);
                    }
                    if (expectMotivation || expectExplanation || expectRemarks) {
                        System.out.println(lineCounter);
                    }
                    assert (!(expectMotivation || expectExplanation || expectRemarks));
                    expectExercise = false;
                    expectMotivation = true;
                    numberOfExercisesFromLastTopic = 0;
                    wasFirstTopic = false;
                } else if (line.startsWith("## M")) {   // motivation for topic found
                    if (!expectMotivation) {
                        System.out.println(lineCounter);
                    }
                    assert (expectMotivation);
                    expectMotivation = false;
                    expectExercise = true;
                } else if (line.startsWith("## ")) {    // exercise for topic found
                    numberOfExercisesFromLastTopic++;
                    if (!expectExercise) {
                        System.out.println(lineCounter);
                    }
                    assert (expectExercise);
                    expectExercise = false;
                    expectExplanation = true;
                } else if (line.startsWith("### E")) {  // explanation for exercise found
                    if (!expectExplanation) {
                        System.out.println(lineCounter);
                    }
                    assert (expectExplanation);
                    expectExplanation = false;
                    expectRemarks = true;
                } else if (line.startsWith("### ")) {   // remarks for exercise found
                    if (!expectRemarks) {
                        System.out.println(lineCounter);
                    }
                    assert (expectRemarks);
                    expectRemarks = false;
                    expectExercise = true;
                }
                lineCounter++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
