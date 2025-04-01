package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Adds a member to an existing group in the address book.
 */
public class AddMemberCommand extends Command {
    public static final String COMMAND_WORD = "addmember";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a member to the group identified "
            + "by the index number used in the displayed group list. "
            + "Parameters: INDEX (must be a positive integer) n/PERSON_NAME\n"
            + "Example: " + COMMAND_WORD + " g/1 n/John Doe";
    public static final String MESSAGE_SUCCESS = "Added member: %1$s to group: %2$s";
    public static final String MESSAGE_PERSON_EXIST = "Member: %1$s already in group: %2$s";
    public static final String MESSAGE_DUPLICATE_PERSONS_FOUND = "More than 1 person with the name %1$s exists, please "
            + "specify details by providing full name of person";

    public static final String MESSAGE_PERSON_DOES_NOT_EXIST = "No person with the name %1$s exists, please "
            + "choose from the list in the address book by providing the full name with appropriate case sensitivity.\n"
            + "If the name is \"Alice Pauline\", please input \"Alice Pauline\", not \"alice pauline\", "
            + "\"alice\" or any other variation";

    private final Index targetIndex;
    private final String member;

    /**
     * Creates an AddMemberCommand to add the specified member to the group at the given index.
     *
     * @param targetIndex The index of the group in the filtered group list.
     * @param member The member to be added.
     */
    public AddMemberCommand(Index targetIndex, String member) {
        requireNonNull(targetIndex, member);
        this.targetIndex = targetIndex;
        this.member = member;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Retrieve the filtered group list
        List<Group> lastShownList = model.getFilteredGroupList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("Invalid Group index");
        }

        Group groupToEdit = lastShownList.get(targetIndex.getZeroBased());

        // Check if the person exists in the address book
        if (!model.hasStringEquivalentPerson(member)) {
            throw new CommandException(String.format(MESSAGE_PERSON_DOES_NOT_EXIST, member));
        }
        // Checks if the person is unique in the address book as there maybe multiple people with same name
        // but different attributes
        if (!model.isPersonUnique(member)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_PERSONS_FOUND, member));
        }
        Person personToAdd = model.getPerson(member);

        // Check if the person is already present
        if (groupToEdit.getMembers().contains(personToAdd)) {
            throw new CommandException(String.format(
                    MESSAGE_PERSON_EXIST, member, groupToEdit.getGroupName().fullName));
        }


        model.deleteGroup(groupToEdit);
        groupToEdit.addMember(personToAdd);
        model.addGroup(groupToEdit);
        model.sortFilteredGroupList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, member, groupToEdit.getGroupName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddMemberCommand otherCommand)) {
            return false;
        }
        return targetIndex == otherCommand.targetIndex && member.equals(otherCommand.member);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .add("sport", member)
                .toString();
    }
}
