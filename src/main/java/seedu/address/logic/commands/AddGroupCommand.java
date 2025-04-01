package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Adds a group in the address book.
 */
public class AddGroupCommand extends Command {

    public static final String COMMAND_WORD = "addgroup";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new group to the address book.\n"
            + "Example: " + COMMAND_WORD + " g/TeamAlpha";

    public static final String MESSAGE_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book.";

    private final Group group;

    /**
     * Creates an AddCommand to add the specified {@code Group}
     */
    public AddGroupCommand(Group group) {
        requireNonNull(group);
        this.group = group;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasGroup(group)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.addGroup(group);
        return new CommandResult(String.format(MESSAGE_SUCCESS, group));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return other instanceof AddGroupCommand
                && ((AddGroupCommand) other).group.equals(this.group);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("group", group)
                .toString();
    }
}
