---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}
* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
* Some features such as addGame, deleteGame were reused from AddressBook-Level3.
--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](diagrams/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="diagrams/ModelClassDiagram.png" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user's preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="diagrams/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### `addgame` feature
The `addgame` command creates and adds a new `Game` object into the addressbook. Each `Game` object has 4 attributes: `Sport`, `LocalDateTime`, `Location` and `UniquePersonList` which are specified using the prefixes g/, dt/, pc respectively with their corresponding values. As a `Game` object is initialised without any participants at first, the `UniquePersonList` field is not mutable during initialisation and thus not included in the command.

#### Implementation
The `Game` object is added into a `UniqueGameList` object whenever `addgame` command is called. This mirrors the `Person` object and `UniquePersonList` object interaction very closely. These are shown on the User Interface via fxml code. Subsequently, the writing to and reading from the addressbook.json which stores the data is facilitated using JsonAdaptedGame and JsonAdaptedPerson which helps to convert the information to and from json readable format.

### `deletegame` feature
Similar to `addgame` feature

### Global Sports List Management

#### Overview

The Global Sports List Management feature allows users to manage a central repository of valid sports that can be assigned to contacts. This feature enhances the sports functionality of the application by providing a consistent set of sports across all contacts.

#### Implementation

The implementation includes several components:

1. **Sport class enhancement**: The `Sport` class has been extended to maintain a static set of valid sports and provide methods for manipulating this set.

2. **UserPrefs integration**: The global sports list's file path is stored in `UserPrefs`, allowing the path to be persisted and retrieved across application sessions.

3. **File storage**: The global sports list is saved to and loaded from a JSON file, ensuring persistence between application runs.

4. **Command implementation**: Three new commands (`CreateSportCommand`, `ListSportsCommand`, and `DeleteSportCommand`) have been implemented to manage the global sports list.

Here's an overview of how the feature works:

![GlobalSportsListSequenceDiagram](images/GlobalSportsListSequenceDiagram.png)

1. On application startup, the `MainApp` class loads the global sports list from the file path specified in `UserPrefs`.
2. When a user adds a new sport using `CreateSportCommand`, the sport is added to the valid sports list in the `Sport` class and saved to the file.
3. The `ListSportsCommand` displays all available sports in alphabetical order with indices.
4. The `DeleteSportCommand` removes a sport from the global list by its index in the alphabetically sorted list.
5. When the application is closed, the global sports list is saved to ensure persistence.

#### Design considerations:

**Aspect: Storage of global sports list**

* **Alternative 1 (current choice):** Store the sports list in a separate JSON file.
  * Pros: Separation of concerns, easier to manage and update independently from contacts.
  * Cons: Requires additional file I/O operations.

* **Alternative 2:** Store the sports list as part of the UserPrefs.
  * Pros: Single file for all user preferences, potentially simpler implementation.
  * Cons: Bloats the UserPrefs object, especially as the sports list grows.

**Aspect: Management of sports list**

* **Alternative 1 (current choice):** Use a static set in the Sport class.
  * Pros: Easy to access from anywhere in the codebase, consistent validation.
  * Cons: Static state can be more difficult to test.

* **Alternative 2:** Store in Model as a field.
  * Pros: Follows the architectural pattern more closely, easier to test.
  * Cons: Requires passing the model to more components, potentially complicating the design.

**Aspect: Command structure for deleting sports**

* **Alternative 1 (current choice):** Have two separate usages for `deletesport` command (with and without sport name).
  * Pros: Intuitive naming, as both commands deal with deleting sports.
  * Cons: Could cause confusion due to the dual behavior.

* **Alternative 2:** Use separate commands like `deleteglobalsport` and `deletepersonsport`.
  * Pros: Clear distinction between different operations.
  * Cons: More commands to learn, potentially less intuitive naming.

#### Future enhancements:

* **Categorization**: Group sports by type (e.g., team sports, individual sports).
* **Popularity tracking**: Track which sports are most commonly used across contacts.
* **Sport details**: Add additional information like required equipment or typical venue types.


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of contacts
* has a need for frequent sporting activities with friends
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage contacts faster than a typical mouse/GUI driven app
so as to reduce the hassle when organizing sports meetups.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                     | I want to …​                                                                                              | So that I can…​                                                                  |
|----------|---------------------------------------------|-----------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| `* * *`  | new user                                    | see usage instructions                                                                                    | refer to instructions when I forget how to use the App                           |
| `* * *`  | user                                        | add a new person                                                                                          | so that I can store their information                                            |
| `* * *`  | user                                        | delete a person                                                                                           | remove entries that I no longer need                                             |
| `* * *`  | user                                        | find a person by name                                                                                     | locate details of persons without having to go through the entire list           |
| `* * *`  | sporty person                               | add a person's preferred sport                                                                            | know who to arrange games with when I want to play a certain sport               |
| `* * *`  | sporty person                               | add a friend's contact number                                                                             | contact them to meet up for games in future                                      |
| `* * *`  | user looking to pick up a new sport         | find friends by sport played                                                                              | invite them to play or teach me the sport                                        |
| `* * *`  | network enthusiast                          | see where my contacts live                                                                                | meet up with them more easily                                                    |
| `* * *`  | impromptu person                            | see where my friends live                                                                                 | see which friends are in the area when I am out and suddenly want to catch up    |
| `* * *`  | caring friend                               | see what sports my friends play                                                                           | arrange a session when I have something to talk about/check on them              |
| `* *`    | sporty person                               | find sports facilities near me                                                                            | play sports there                                                                |
| `* *`    | user                                        | hide private contact details                                                                              | protect the contact details of persons in my address book                        |
| `* *`    | student                                     | receive suggested sports venues based on my group's location                                              | arrange a meetup location which is fair and convenient                           |
| `* *`    | user who prefers structured planning        | book facilities directly through the app                                                                  | reduce the hassle of navigating multiple booking applications                    |
| `* *`    | busy professional                           | quickly check for the nearest sports facility before heading down                                         | save time                                                                        |
| `* *`    | user who dislikes long commutes             | set a maximum travel distance to be used to filter out addresses that are too far away from my location   | receive suggestions from the application which fall within my preferred range    |
| `* *`    | user                                        | be able to edit an existing contact                                                                       | update their contact details                                                     |
| `* *`    | working professional                        | add my favourite sports venues                                                                            | quickly suggest them for meetups                                                 |
| `* *`    | thrifty user                                | be able to filter results only to show free to use facilities                                             | enjoy sports without paying for private courts                                   |
| `* *`    | tennis player                               | be reminded to bring my racket                                                                            | save the trouble of having to borrow a racket at the last minute                 |
| `* *`    | sporty person                               | set my friend's preferred play locations                                                                  | know where to meet them for games                                                |
| `* *`    | working adult                               | tag contacts with custom labels                                                                           | categorize them by friends/work                                                  |
| `* *`    | sports coach                                | track where my students live                                                                              | organize training sessions at convenient locations                               |

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case 1: Delete a person**

**MSS**

1.  User requests to list persons
2.  AddressBook shows a list of persons
3.  User requests to delete a specific person in the list
4.  AddressBook deletes the person
    Use case ends.
    **Extensions**
* 2a. The list is empty.
  Use case ends.
* 3a. The given index is invalid.
  * 3a1. AddressBook shows an error message.

    Use case resumes at step 2.

**Use case 2: Tag a contact**

**MSS**

1.  User requests to list persons
2.  AddressBook shows a list of persons
3.  User requests to tag a specific person in the list
4.  AddressBook updates the person's contact details

    Use case ends.

    **Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The person is not in the list.

  * 3a1. AddressBook shows an error message.

    Use case resumes at step 2.

**Use case 3: Filter by location**

**MSS**

1.  User requests to list persons
2.  AddressBook shows a list of persons
3.  User enters a location to filter the list
4.  AddressBook updates the list shown to user

    Use case ends.

    **Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given location is invalid.

  * 3a1. AddressBook shows an error message.

    Use case resumes at step 2.

* 4a. The given location does not belong to any user.

  * 4a1. AddressBook shows an error message.

    Use case resumes at step 2.

**Use case 4: Edit a contact**

**MSS**

1.  User searches for a person
2.  AddressBook shows matching result(s)
3.  User requests to view contact details
4.  AddressBook shows contact details
5.  User selects field to update details
6.  AddressBook reflects updated contact details

    Use case ends.

    **Extensions**

* 2a. The list is empty.

  Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The system must be designed for a single user only, user data is private and not shared between different users.
5. Users should be able to run the application from a single executable JAR file.
6. The system must automatically save data after each user edit to ensure data persistence.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **JAR File**: A compressed package that bundles Java classes, resources metadata into a single executable file

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

### Launch and shutdown

1. Initial launch

2. Download the jar file and copy into an empty folder.

3. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

4. Saving window preferences.

5. Resize the window to an optimum size. Move the window to a different location. Close the window.

6. Re-launch the app by double-clicking the jar file.<br>
     Expected: The most recent window size and location is retained. However, if the window was closed in light mode, it will start in dark mode as we chose to reduce the glare when the application is booted up.

### Adding a person

#### Adding a person to contact list

1. Prerequisites: None.

2. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/John Street, #01-01 t/bestfriend s/tennis pc/402001`<br>
   Expected: John Doe contact is added to the list. Details of the contact shown in the status message. PersonCard is found in the list on the left.

3. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/John Street, #01-01 t/bestfriend s/tennis pc/123456`<br>
   Expected: No person is added due to invalid postal code. Error details shown in the status message.

4. Other incorrect add commands to try: <br>
   `add n/John Doe p/ e/johnd@example.com a/John Street, #01-01 s/tennis pc/402001`, <br>
   `add n/John Doe p/98765432 e/johnd@example.com a/, #01-01 s/tennis pc/402001`, `...` <br>
   Expected: Similar to previous.

### Editing a person

#### Editing a person while at least one person is being shown

1. Prerequisites: List all persons using the `list` command or List persons using the 'find' command. At least 1 person in the list.

2. Test case: `edit 1 n/Alicia Tay`<br>
   Expected: First contact has name edited to \"Alicia Tay\". Details of the outcome of the edit shown in the status message.

3. Test case: `edit 1 n/`<br>
   Expected: No name is edited due to erroneous name field. Details of the error shown in the status message.

4. Other incorrect edit commands to try: `edit`, `edit x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.

### Deleting a person

#### Deleting a person while all persons are being shown

1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

2. Test case: `delete 1`<br>
   Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

3. Test case: `delete 0`<br>
   Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.

### Creating a sport in the global list

#### Creating a sport

1. Prerequisites: None.

2. Test case: `createsport s/archery`<br>
   Expected: Archery will be created in the global list. Details of the outcome of the createsport shown in the status message.

3. Other incorrect createsport commands to try: `createsport`, `...` <br>
   Expected: Similar to previous.

### Listing global sports

#### Lists all global sports.

1. Prerequisites: None

2. Test case: `listsports`<br>
   Expected: Lists all registered sports in the status message.

3. Other incorrect listsports commands to try: `listsports 1`, `deletesport abc`, `...`<br>
   Expected: Similar to previous.

### Deleting a sport from global list

#### Deleting a sport from list of registered sports

1. Prerequisites: At least 1 sport in the global list.

2. Test case: `deletesport 1`<br>
   Expected: The first sport in the list, if it exists, is deleted. Details of the outcome of the deletesport shown in the status message.

3. Test case: `deletesport 0`<br>
   Expected: No sport is deleted due to invalid index. Details of the outcome of the deletesport shown in the status message.

4. Other incorrect deletesport commands to try: `deletesport`, `deletesport x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.

### Adding a sport to a person

#### Adding a sport to a person while at least one person is being shown

1. Prerequisites: List all persons using the `list` command or List persons using the 'find' command. At least 1 person in the list.

2. Test case: `addsport 1 s/basketball`<br>
   Expected: First contact has basketball added to its registered sports, unless the sport already exists in their contact. Details of the outcome of the addsport shown in the status message.

3. Test case: `addsport 1 s/chocolate`<br>
      Expected: No sport is added due to invalid sport. Details of the outcome of the addsport shown in the status message.

4. Test case: `addsport 0 s/basketball`<br>
   Expected: No sport is added due to invalid index. Details of the outcome of the addsport shown in the status message.

5. Other incorrect addsport commands to try: `addsport`, `addsport x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.


### Deleting a sport from a person

#### Deleting a sport from a person while at least one person is being shown

1. Prerequisites: List all persons using the `list` command or List persons using the 'find' command. At least 1 person in the list.

2. Test case: `deletesport 1 s/basketball`<br>
   Expected: First contact has basketball deleted from its registered sports, if the sport already exists in their contact. Details of the outcome of the deletesport shown in the status message.

3. Test case: `deletesport 1 s/chocolate`<br>
   Expected: No sport is deleted due to invalid sport. Details of the outcome of the deletesport shown in the status message.

4. Test case: `deletesport 0 s/basketball`<br>
   Expected: No sport is added due to invalid index. Details of the outcome of the deletesport shown in the status message.

6. Other incorrect deletesport commands to try: `deletesport`, `deletesport x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.

### Finding persons who play specific sports

#### Displaying a list of person who play specific sports

1. Prerequisites: At least one person must play one of the sports in the list of sports provided.

2. Test case: `findsport s/basketball`<br>
   Expected: Contacts who have basketball in their registered sports will be shown. Details of the outcome of the findsport shown in the status message.

3. Test case: `findsport s/chocolate`<br>
   Expected: Error message is shown as sport is not one of the registered sports. Details of the outcome of the findsport shown in the status message.

4. Other incorrect findsport commands to try: `findsport`, `findsport all`, `...`<br>
   Expected: Similar to previous.

### Finding persons who play a particular sport near a location

#### Displaying a list of person who play specific sports

1. Prerequisites: At least one person must play one of the sports in the list of sports provided. Postal Code must be valid.

2. Test case: `findsport pc/402001 s/basketball`<br>
   Expected: Contacts who have basketball in their registered sports will be shown by ascending order of distance from the specified location. Details of the outcome of the findsport shown in the status message.

3. Test case: `findsport pc/123456 s/tennis`<br>
   Expected: Error message is shown as postal code is not valid. Details of the outcome of the findsport shown in the status message.

4. Test case: `findsport pc/402001 s/chocolate`<br>
   Expected: Error message is shown as sport is not in registered list of sports. Details of the outcome of the findsport shown in the status message.

5. Other incorrect findsport commands to try: `findsport`, `findsport all`, `...`<br>
   Expected: Similar to previous.

### Adding a game

#### Adds a game to the gamelist

1. Prerequisites: Game must be a registered sport. Date time must follow specified format. Postal Code must be valid.

2. Test case: `addgame g/volleyball  dt/2025-04-04T15:00:00 pc/402001`<br>
   Expected: Game will be added and the details of the game will be shown in the status message of addgame.

3. Test case: `addgame g/tennis`<br>
   Expected: Error message is shown as date time and postal code not added. Details of the outcome of the addgame shown in the status message.

4. Other incorrect addgame commands to try: `addgame`, `addgame g/chocolate`, `...` <br>
   Expected: Similar to previous.

### Deleting a game

#### Deletes a game from gamelist

1. Prerequisites: At least one game must exist.

2. Test case: `deletegame 1`<br>
   Expected: Game at index 1 will be deleted. Details of the outcome of the deletegame shown in the status message.

3. Test case: `deletegame 0`<br>
   Expected: Error message is shown as postal index is not valid. Details of the outcome of the deletegame shown in the status message.

4. Other incorrect deletegame commands to try: `deletegame`, `deletegame x`, `...` (where x is larger than the list size)<br>
   Expected: Similar to previous.

### Adding a member to a game

#### Adds a member to a game in the gamelist

1. Prerequisites: At least one game must exist. The member must exist in the person list.

2. Test case: `addmember g/1 n/Alice Pauline`<br>
   Expected: \"Alice Pauline\" will be added to game at index 1. Details of the outcome of the addmember shown in the status message.

3. Test case: `addmember g/0 n/Alice Pauline`<br>
   Expected: Error message is shown as index is not valid. Details of the outcome of the addmember shown in the status message.

4. Test case: `addmember g/1 n/Banana Man`<br>
   Expected: Error message is shown as contact is not in the person list. Details of the outcome of the addmember shown in the status message.

5. Other incorrect addmember commands to try: `addmember g/1`, `addmember all`, `...`<br>
   Expected: Similar to previous.

### Deleting a member from a game

#### Deletes a member from a game in the gamelist.

1. Prerequisites: At least one game must exist in the gamelist. Member must exist in the person list.

2. Test case: `deletemember g/1 n/Alice Pauline`<br>
   Expected: \"Alice Pauline\" will be deleted from game at index 1. Details of the outcome of the deletemember shown in the status message.

3. Test case: `deletemember g/0 n/Alice Pauline`<br>
   Expected: Error message is shown as index is not valid. Details of the outcome of the deletemember shown in the status message.

4. Test case: `deletemember g/1 n/Banana Man`<br>
   Expected: Error message is shown as contact is not in the person list. Details of the outcome of the deletemember shown in the status message.

5. Other incorrect deletemember commands to try: `deletemember g/1`, `deletemember all`, `...`<br>
   Expected: Similar to previous.
