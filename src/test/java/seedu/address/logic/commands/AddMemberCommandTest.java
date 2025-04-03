package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGames.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.game.Game;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code AddMemberCommand}.
 */
public class AddMemberCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMemberCommand(INDEX_FIRST_OBJECT, null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Game gameToModify = model.getFilteredGameList().get(INDEX_FIRST_OBJECT.getZeroBased());
        Person personToAdd = TypicalPersons.AMY;
        String personName = personToAdd.getName().fullName;
        // ensure that person is not already in game, and person is in list to make it a valid addition
        model.addPerson(personToAdd);
        AddMemberCommand addMemberCommand = new AddMemberCommand(INDEX_FIRST_OBJECT, personName);

        String expectedMessage = String.format(AddMemberCommand.MESSAGE_SUCCESS,
                personName, gameToModify.getSport().sportName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Game modifiedGame = createGameWithAdditionalPerson(gameToModify, personToAdd);
        expectedModel.setGame(gameToModify, modifiedGame);

        assertEquals(addMemberCommand.execute(model), new CommandResult(expectedMessage));
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_duplicateMember_throwsCommandException() {
        Game gameToModify = model.getFilteredGameList().get(INDEX_FIRST_OBJECT.getZeroBased());
        Person personToAdd = gameToModify.getParticipants().get(0);
        String personName = personToAdd.getName().fullName;

        AddMemberCommand addMemberCommand = new AddMemberCommand(
                INDEX_FIRST_OBJECT, personName);
        assertThrows(CommandException.class, () -> addMemberCommand.execute(model));
    }

    @Test
    public void execute_invalidMember_throwsCommandException() {
        Person invalidPerson = TypicalPersons.AMY;
        String personName = invalidPerson.getName().fullName;
        AddMemberCommand addMemberCommand = new AddMemberCommand(
                INDEX_FIRST_OBJECT, personName);

        assertThrows(CommandException.class,
                String.format(AddMemberCommand.MESSAGE_PERSON_NOT_FOUND, personName), (
                ) -> addMemberCommand.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGameList().size() + 1);
        Person personToAdd = model.getFilteredPersonList().get(0);
        String personName = personToAdd.getName().fullName;
        AddMemberCommand addMemberCommand = new AddMemberCommand(outOfBoundIndex, personName);

        assertThrows(CommandException.class,
                String.format(AddMemberCommand.MESSAGE_INVALID_GAME_INDEX, outOfBoundIndex.getOneBased()), (
                ) -> addMemberCommand.execute(model));
    }

    @Test
    public void equals() {
        Sport soccer = new Sport("soccer");
        Sport basketball = new Sport("basketball");
        AddSportCommand addSoccerCommand = new AddSportCommand(INDEX_FIRST_OBJECT.getZeroBased(), soccer);
        AddSportCommand addBasketballCommand = new AddSportCommand(INDEX_FIRST_OBJECT.getZeroBased(), basketball);
        AddSportCommand addSoccerSecondCommand = new AddSportCommand(INDEX_SECOND_OBJECT.getZeroBased(), soccer);

        // same object -> returns true
        assertTrue(addSoccerCommand.equals(addSoccerCommand));

        // same values -> returns true
        AddSportCommand addSoccerCommandCopy = new AddSportCommand(INDEX_FIRST_OBJECT.getZeroBased(), soccer);
        assertTrue(addSoccerCommand.equals(addSoccerCommandCopy));

        // different types -> returns false
        assertFalse(addSoccerCommand.equals(1));

        // null -> returns false
        assertFalse(addSoccerCommand.equals(null));

        // different sport -> returns false
        assertFalse(addSoccerCommand.equals(addBasketballCommand));

        // different index -> returns false
        assertFalse(addSoccerCommand.equals(addSoccerSecondCommand));
    }

    /**
     * Creates a new person with all the same fields as the original, plus an additional sport.
     */
    private Game createGameWithAdditionalPerson(Game game, Person person) {
        List<Person> participantList = new ArrayList<>(game.getParticipants());
        participantList.add(person);

        return new Game(
                game.getSport(),
                game.getDateTime(),
                game.getLocation(),
                participantList);
    }
}
