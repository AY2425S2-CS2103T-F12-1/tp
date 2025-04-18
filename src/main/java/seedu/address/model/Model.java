package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.game.Game;
import seedu.address.model.person.Location;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluates to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();



    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same
     * as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Sorts the filtered person list by distance from the given location.
     *
     * @param locationToBeCompared The location to compare the distance to.
     */
    void sortFilteredPersonListByDistance(Location locationToBeCompared);


    /**
     * Sorts the filtered person list by distance from the given location.
     */
    void sortFilteredPersonListAlphabetically();

    /**
     * Replaces the given game {@code target} in the list with {@code editedGame}.
     * {@code target} must exist in the address book.
     * The game identity of {@code editedGame} must not be the same as another existing game in the address book.
     */
    void setGame(Game target, Game editedGame);

    /** Returns an unmodifiable view of the filtered game list. */
    ObservableList<Game> getFilteredGameList();

    /**
     * Updates the filter of the filtered game list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGameList(Predicate<Game> predicate);

    /**
     * Returns true if a game with the same identity as {@code game} exists in the address book.
     *
     * @param game The game to check.
     * @return true if the game exists, false otherwise.
     */
    boolean hasGame(Game game);

    /**
     * Adds a new game to the address book.
     * The game must be unique and not already exist.
     *
     * @param game The game to add.
     */
    void addGame(Game game);

    /**
     * Deletes the given game.
     * The game must exist in the address book.
     */
    void deleteGame(Game target);

    /**
     * Returns the list of all games in the address book.
     *
     * @return The list of games.
     */
    ObservableList<Game> getGameList();

    /**
     * Checks if a given name is unique in the address book
     */
    int isPersonUnique(String name);

    /**
     * Returns the single Person matching the given name (case-insensitive),
     * or throws an exception if there are multiple or no matches.
     */
    Person getPerson(String name);

}
