package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.LocationUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.game.Game;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sport;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String postalCode} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code postalCode} is invalid.
     */
    public static String parsePostalCode(String postalCode) throws ParseException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (trimmedPostalCode.isEmpty()) {
            throw new ParseException("Postal code cannot be empty.");
        }
        if (!LocationUtil.isValidPostalCode(trimmedPostalCode)) {
            throw new ParseException("Invalid postal code. Please enter a valid Singapore postal code.");
        }
        return trimmedPostalCode;
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String gameName} and {@code String dateTime} into a {@code Game}.
     * Leading and trailing whitespaces will be trimmed.
     * The {@code Name} class is used to validate the game name and the dateTime is parsed using ISO-8601 format.
     *
     * @throws ParseException if the game name or date/time is invalid.
     */
    public static Game parseGame(String gameName, String dateTime, String location) throws ParseException {
        requireNonNull(gameName);
        requireNonNull(dateTime);
        String trimmedGameName = gameName.trim();
        if (!Sport.isValidSport(trimmedGameName)) {
            throw new ParseException(Sport.getMessageConstraints());
        }
        return new Game(new Sport(trimmedGameName), parseDateTime(dateTime), LocationUtil.createLocation(location));
    }

    /**
     * Parses a {@code String dateTime} into a {@code LocalDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     * The dateTime is expected to be in ISO-8601 format (e.g. 2021-09-20T15:00:00).
     *
     * @throws ParseException if the dateTime string is invalid.
     */
    public static java.time.LocalDateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        try {
            return java.time.LocalDateTime.parse(trimmedDateTime);
        } catch (java.time.format.DateTimeParseException e) {
            String msg = "Invalid date/time format. Expected ISO-8601 format (e.g. 2021-09-20T15:00:00).";
            throw new ParseException(msg, e);
        }
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String sport} into a {@code Sport}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sport} is invalid.
     */
    public static Sport parseSport(String sport) throws ParseException {
        requireNonNull(sport);
        String trimmedSport = sport.trim();
        if (!Sport.isValidSport(trimmedSport.toLowerCase())) {
            throw new ParseException(Sport.getMessageConstraints());
        }

        return new Sport(trimmedSport);
    }

    /**
     * Parses {@code Collection<String> sports} into a {@code List<Sport>}.
     */
    public static List<Sport> parseSports(Collection<String> sports) throws ParseException {
        requireNonNull(sports);
        List<Sport> sportList = new ArrayList<>();
        for (String sportStr : sports) {
            // This call will validate each sport string individually.
            sportList.add(parseSport(sportStr));
        }
        return sportList;
    }
}
