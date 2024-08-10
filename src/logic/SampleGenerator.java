package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class SampleGenerator extends ConverterInterface {
    
    final String nameOfMdFile = "src/resources/markdownToConvert.md";

    final List<Integer> lineNumberOfTopicStarts = new ArrayList<>();
    final Map<Integer, Pair<Integer, List<Integer>>> lineOfTopicToLinesOfMotivationAndExercise = new HashMap<>();

    public SampleGenerator(final String path, final Language language) {
        this.path = path;
        switch (language) {
            case GERMAN:
                this.fileName = "exercisesGerman.md";
                break;
            case ENGLISH:
                this.fileName = "exercisesEnglish.md";
                break;
            default:
                throw new IllegalArgumentException("SampleGenerator: Invalid language!");
        }
        this.validateFileEnding(".md");
    }

    @Override
    public void convert() {
        this.indexing();
        final Random random = new Random();

        // selecting warm-up material
        final int warmUpIndex = this.lineNumberOfTopicStarts.remove(0);
        final Pair<Integer, List<Integer>> warmUpPair = this.lineOfTopicToLinesOfMotivationAndExercise.get(warmUpIndex);
        final List<Integer> warumUpList = warmUpPair.second;
        final Set<Integer> selectedWarmUp = new HashSet<>();
        selectedWarmUp.add(warumUpList.get(random.nextInt(warumUpList.size())));
        selectedWarmUp.add(warumUpList.get(random.nextInt(warumUpList.size())));

        // selecting topics
        final List<Integer> selectedTopics = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            final int currentTopicIndex = this.lineNumberOfTopicStarts.get(random.nextInt(this.lineNumberOfTopicStarts.size()));
            if (selectedTopics.contains(currentTopicIndex)) {
                i--;
                continue;
            }
            selectedTopics.add(currentTopicIndex);
        }

        // selecting exercises for each topic
        final List<Integer> selectedExercises = new ArrayList<>();
        for (final Integer topicIndex : selectedTopics) {
            final Pair<Integer, List<Integer>> topicPair = this.lineOfTopicToLinesOfMotivationAndExercise.get(topicIndex);
            final List<Integer> exerciseList = topicPair.second;
            if (exerciseList.size() <= 2) {
                selectedExercises.addAll(exerciseList);
            } else {
                for (int i = 0; i < 2; i++) {
                    final int currentExerciseIndex = exerciseList.get(random.nextInt(exerciseList.size()));
                    if (!selectedExercises.contains(currentExerciseIndex)) {
                        selectedExercises.add(currentExerciseIndex);
                    }
                }
            }
        }
        this.lineNumberOfTopicStarts.add(0, warmUpIndex);

        // getting every selected index together
        final List<Integer> allSelected = new ArrayList<>();
        allSelected.add(warmUpIndex);
        allSelected.addAll(selectedWarmUp);
        allSelected.addAll(selectedTopics);
        allSelected.addAll(selectedExercises);
        allSelected.sort(null);
        final List<Integer> all = new ArrayList<>(this.getSortedIndexList());

        // writing to markdownToConvert.md
        int currentIndex = 0;
        final File file = new File(this.path + this.fileName);
        try {
            final Scanner scanner = new Scanner(file);
            final FileWriter fileWriter = new FileWriter(this.nameOfMdFile);
            boolean linesAreCurrentlyWritten = false;
            while (scanner.hasNext()) {
                final String line = scanner.nextLine();
                if (linesAreCurrentlyWritten && all.contains(currentIndex)) {
                    linesAreCurrentlyWritten = false;
                }
                if (!linesAreCurrentlyWritten && allSelected.contains(currentIndex)) {
                    linesAreCurrentlyWritten = true;
                }
                if (linesAreCurrentlyWritten) {
                    fileWriter.write(line + "\n");
                }
                currentIndex++;
            }
            scanner.close();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getSortedIndexList() {
        final List<Integer> result = new ArrayList<>(this.lineNumberOfTopicStarts);
        for (final Integer key : this.lineOfTopicToLinesOfMotivationAndExercise.keySet()) {
            result.add(key);
            final Pair<Integer, List<Integer>> pair = this.lineOfTopicToLinesOfMotivationAndExercise.get(key);
            result.add(pair.first);
            result.addAll(pair.second);
        }
        result.sort(null);
        return result;
    }

    private void indexing() {
        this.lineNumberOfTopicStarts.clear();
        this.lineOfTopicToLinesOfMotivationAndExercise.clear();
        final File file = new File(this.path + this.fileName);
        try {
            final Scanner scanner = new Scanner(file);
            int lineCounter = 0;
            int lastIndexOfLineNumberOfTopicStarts = -1;
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (line.startsWith("# ")) {
                    this.lineNumberOfTopicStarts.add(lineCounter);
                    lastIndexOfLineNumberOfTopicStarts = lineCounter;
                } else if (line.startsWith("## Motivation")) {
                    this.lineOfTopicToLinesOfMotivationAndExercise.put(
                        lastIndexOfLineNumberOfTopicStarts,
                        new Pair<>(lineCounter, new ArrayList<>())
                    );
                } else if (line.startsWith("## ")) {
                    this.lineOfTopicToLinesOfMotivationAndExercise.get(
                        lastIndexOfLineNumberOfTopicStarts
                    ).second.add(lineCounter);
                }
                lineCounter++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
