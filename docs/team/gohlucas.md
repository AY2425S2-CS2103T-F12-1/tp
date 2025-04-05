---
layout: page
title: Lucas' Project Portfolio Page
---

### Project: FitFriends

Fitfriends is a desktop address book application where the user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 12 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to add sports to a contact.
  * What it does: allows the user add sports to a contact one or more at a time.
  * Justification: This is a core feature of the product as it is a sports based address book.
  * Credits: *reused idea from the tags field present in address book 3.*

* **New Feature**: Added the ability to find contacts by sport.
  * What it does: allows the user to find contacts by sport in addition to the existing find by name command.
  * Justification: This feature logically makes sense to include as our address book is sports based.
  * Credits: *reused idea from the find command present in address book 3.*

* **New Feature**: Added the view functionality for sports in a contact.
  * What it does: allows the user to view the sports added to a contact.
  * Justification: This feature improves the product significantly because a user can see what sports each of their contact plays.
  * Credits: *reused idea by adapting attributes into .fxml files such as PersonListCard.fxml and PersonListPanel.fxml from address book 3*

* **New Feature**: Added the view functionality for games.
  * What it does: allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
  * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.
  * Credits: *reused idea from the existing .fxml files such as PersonListCard.fxml and PersonListPanel.fxml from address book 3*

* **New Feature**: Added the ability to delete games.
  * What it does: allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
  * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.
  * Credits: *{mention here if you reused any code/ideas from elsewhere or if a third-party library is heavily used in the feature so that a reader can make a more accurate judgement of how much effort went into the feature}*

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=gohlucas&tabRepo=AY2425S2-CS2103T-F12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Enhancements to existing features**:
    * Updated the GUI to allow tags, sports and members to be displayed neatly (Pull requests [\#95]())

* **Documentation**:
    * User Guide:
        * Added documentation for the features `addmember` and `deletemember` [\#244]()
    * Developer Guide:
        * Added User Stories and Use Cases

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#130]()
    

