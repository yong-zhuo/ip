package matcha.command;

import matcha.exception.MatchaException;
import matcha.storage.Storage;
import matcha.tasklist.TaskList;

/**
 * Represents a command to update a task in the task list.
 */
public class UpdateTaskCommand extends Command {
    private String commandType;
    private String[] inputWords;

    /**
     * Constructor for UpdateTaskCommand.
     *
     * @param inputWords Array of words from user input.
     * @param commandType Type of update task command.
     */
    public UpdateTaskCommand(String[] inputWords, String commandType) {
        this.commandType = commandType;
        this.inputWords = inputWords;
    }

    /**
     * Executes the update task command given the task list, ui and storage objects.
     *
     * @param tasks Task list to update.
     * @param storage Storage object to save tasks to file.
     * @return The response to the user.
     * @throws MatchaException If the task number is not provided or is invalid.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws MatchaException {
        if (inputWords.length != 2) {
            throw new MatchaException("Please enter the task number of the task you want to " + commandType + ".");
        }

        switch (commandType) {
        case "mark":
            markTask(tasks);
            break;
        case "unmark":
            unmarkTask(tasks);
            break;
        default:
            throw new MatchaException("Invalid command to update tasks!");
        }
        //save tasks to file
        storage.saveTasks(tasks.getTasks());
        return super.getResponse();
    }

    /**
     * Marks a task as done in the task list and prints out the task details
     *
     * @param tasks Task list to update.
     * @throws MatchaException If the task number is not provided or is invalid.
     */
    private void markTask(TaskList tasks) throws MatchaException {
        int taskNum = 0;

        try {
            taskNum = Integer.parseInt(inputWords[1]) - 1;
        } catch (NumberFormatException e) {
            throw new MatchaException("Please enter the task number of the task you want to\nmark as done.");
        }
        assert taskNum <= tasks.getSize() || taskNum > 0 : "Task number is invalid";
        if (taskNum < 0 || taskNum >= tasks.getSize()) {
            throw new MatchaException("This task does not exist!");
        }
        tasks.getTask(taskNum).markDone();
        super.setResponse("I have successfully marked this task as done:\n" + tasks.getTask(taskNum).toString());
    }

    /**
     * Unmarks a task in the task list and prints out the task details
     *
     * @param tasks Task list to update.
     * @throws MatchaException If the task number is not provided or is invalid.
     */
    private void unmarkTask(TaskList tasks) throws MatchaException {
        int taskNum = 0;

        try {
            taskNum = Integer.parseInt(inputWords[1]) - 1;
        } catch (NumberFormatException e) {
            throw new MatchaException("Please enter the task number of the task you want to\nmark as not done.");
        }
        assert taskNum <= tasks.getSize() || taskNum > 0 : "Task number is invalid";
        if (taskNum < 0 || taskNum >= tasks.getSize()) {
            throw new MatchaException("This task does not exist!");
        }

        tasks.getTask(taskNum).markNotDone();
        super.setResponse("I have successfully marked this task as not done:\n" + tasks.getTask(taskNum).toString());
    }
}
