## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

# Project Documentation

## How to use

The functionality of the window is self-explanatory. The output file of the program is `.pdf`, that won't be downloaded. If you wanna store a generated output file, you must save it from your selected pdf-reader manually.

The pool of exercises is in the `exercises` folder. If you want to add exercises to the `.md` files, follow the given structure. I.e.:

To introduce a new topic, you write this below all other topics:

```markdown
# <new_topic>
```

Where you replace "<new_topic>" with the name of your topic. Below that, the program expects *at least* the following 2 subsections:

```markdown
## Motivation/Explanation

Here comes the motivation/explanation for this topic. In case you don't want to add neither motivation nor explanation, you can simply leave this subsection empty, but the header must be present.
```

and *at least* one subsection with this signature:

```markdown
## Exercise: <exercise_name>

### Explanation

Here goes the explanation of the exercise. If your explanation requires multiple paragraphs, you can add a second one by having one blank line between the paragraphs, you want to seperate. Just like this:

Here starts the second paragraph.

### Remarks

- Here goes a list of remarks, which is formated like this list itself.
- If you don't have any remarks, leave this section empty.
- This section is generally useful to write down some things, the acting people should keep in mind during the exercise.
- Also useful to specify the number of people needed for this exercise.
- Or for specifying the time.
```

**Note:** You can have as many exercise-subsections below a newly introduced topic as you wish. You can also list the same exercise below multiple topics simultaneously, which can be useful, since the algorithm sorts exercises by topics they belong to. You also have to use the same structure when adding a new exercise to an already existing topic.
