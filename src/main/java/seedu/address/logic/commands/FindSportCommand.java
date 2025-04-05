package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPORT;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Sport;
import seedu.address.model.person.SportContainsKeywordsPredicate;


/**
 * Finds and lists all persons in address book who play a sport contained in any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindSportCommand extends Command {

    public static final String COMMAND_WORD = "findsport";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who play a sport contained in "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_POSTAL_CODE + "POSTAL_CODE] "
            + PREFIX_SPORT + "SPORT_KEYWORD "
            + PREFIX_SPORT + "[MORE_SPORT_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_POSTAL_CODE + "018906 "
            + PREFIX_SPORT + "badminton "
            + PREFIX_SPORT + "volleyball "
            + PREFIX_SPORT + "cricket";

    protected final SportContainsKeywordsPredicate predicate;
    protected final List<String> sportKeywordList;

    /**
     * Creates a FindSportCommand object to find persons who play certain sports
     * @param predicate test if a person has sports which are contained in user input lis
     * @param sportKeywordList user input list to check for valid sports
     */
    public FindSportCommand(SportContainsKeywordsPredicate predicate, List<String> sportKeywordList) {
        this.predicate = predicate;
        this.sportKeywordList = sportKeywordList;
    }

    // Update this definition to use a method that can be evaluated at runtime
    public static String getInvalidSportMessage() {
        // Use the sorted list of sports to display them in alphabetical order
        List<String> sortedSports = Sport.getSortedValidSports();
        StringBuilder message = new StringBuilder("Invalid sport found. Allowed sports: \n");

        for (int i = 0; i < sortedSports.size(); i++) {
            message.append(String.format("%d. %s\n", i + 1, sortedSports.get(i)));
        }

        message.append("\nExample: ")
               .append(COMMAND_WORD)
               .append(" ")
               .append(PREFIX_SPORT)
               .append("badminton ")
               .append(PREFIX_SPORT)
               .append("volleyball ")
               .append(PREFIX_SPORT)
               .append("cricket");

        return message.toString();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // Use Sport.isValidSport to validate each spor
        boolean hasInvalidSport = sportKeywordList.stream()
                .anyMatch(sport -> !Sport.isValidSport(sport));

        if (hasInvalidSport) {
            return new CommandResult(getInvalidSportMessage());
        }
        model.updateFilteredPersonList(predicate);
        model.sortFilteredPersonListAlphabetically();
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindSportCommand)) {
            return false;
        }

        FindSportCommand otherFindSportCommand = (FindSportCommand) other;
        return predicate.equals(otherFindSportCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
