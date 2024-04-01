package seedu.hirehub.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.hirehub.model.application.Application;
import seedu.hirehub.model.job.Job;
import seedu.hirehub.model.person.Person;

/**
 * An UI component that displays information of a {@code Application}.
 */
public class ApplicationCard extends UiPart<Region> {
    private static final String FXML = "ApplicationListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Application application;
    public final Person person;
    public final Job job;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label country;
    @FXML
    private Label email;
    @FXML
    private Label comment;
    @FXML
    private FlowPane tags;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label status;
    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public ApplicationCard(Application application, int displayedIndex) {
        super(FXML);
        this.application = application;
        this.job = application.getJob();
        this.person = application.getPerson();
        id.setText(String.valueOf(displayedIndex));
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        country.setText(person.getCountry().getDisplayCountry());
        email.setText(person.getEmail().value);
        comment.setText(person.getComment().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        title.setText(job.getTitle());
        description.setText(job.getDescription());
        status.setText(application.getStatus().value);
    }
}
