package matcha.command;

import matcha.exception.MatchaException;
import matcha.storage.Storage;
import matcha.task.Task;
import matcha.tasklist.TaskList;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteTaskCommand extends Command {
    private String[] inputWords;

    /**
     * Constructor for DeleteTaskCommand.
     *
     * @param inputWords Array of words from user input.
     */
    public DeleteTaskCommand(String[] inputWords) {
        this.inputWords = inputWords;
    }

    /**
     * Deletes the given task and saves the updated task list to file.
     *
     * @param tasks Task list to delete task from.
     * @param storage Storage object to save tasks to file.
     * @return The response to the user.
     * @throws MatchaException If the task number is not provided or is invalid.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) throws MatchaException {
        if (inputWords.length != 2) {
            throw new MatchaException("Please enter the task number of the task you want to delete.");
        }

        int taskNum = 0;
        try {
            taskNum = Integer.parseInt(inputWords[1]) - 1;
        } catch (NumberFormatException e) {
            throw new MatchaException("Please enter the task number of the task you want to delete.");
        }
        assert taskNum <= tasks.getSize() || taskNum > 0 : "Task number is invalid";
        if (taskNum < 0 || taskNum >= tasks.getSize()) {
            throw new MatchaException("This task does not exist!");
        }
        //delete task from task list
        Task taskToRemove = tasks.getTask(taskNum);
        tasks.deleteTask(taskNum);
        String deleteMessage = "Alright, I have removed this task:\n" + tasks.showTask(taskToRemove);
        //update tasks to file
        storage.saveTasks(tasks.getTasks());
        super.setResponse(deleteMessage);
        return super.getResponse();
    }
}
