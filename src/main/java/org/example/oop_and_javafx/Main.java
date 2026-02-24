package org.example.oop_and_javafx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private TableView<Student> table;
    private ObservableList<Student> data = FXCollections.observableArrayList();
    private int idCounter = 1;

    private TextField tfFirst = new TextField(), tfLast = new TextField(),
            tfDept = new TextField(), tfMajor = new TextField(),
            tfEmail = new TextField(), tfImageUrl = new TextField();

    private Button btnClear = new Button("Clear"), btnAdd = new Button("Add"),
            btnDelete = new Button("Delete"), btnEdit = new Button("Edit");

    private ImageView avatarImageView;
    private final String DEFAULT_AVATAR = getClass().getResource("/images/profile icon.png").toExternalForm();

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());
        root.setLeft(createLeftPanel());
        root.setCenter(createTableView());
        root.setRight(createRightPanel());
        root.setBottom(createBottomPanel());

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("FSC CSC325 - Full Stack Project");
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File"), editMenu = new Menu("Edit"),
                themeMenu = new Menu("Theme"), helpMenu = new Menu("Help");

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exitItem);
        menuBar.getMenus().addAll(fileMenu, editMenu, themeMenu, helpMenu);
        return menuBar;
    }

    private TableView<Student> createTableView() {
        table = new TableView<>();
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Student, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setMaxWidth(45);
        colId.setMinWidth(35);

        String[] cols = {"First Name", "Last Name", "Department", "Major", "Email"};
        String[] props = {"firstName", "lastName", "department", "major", "email"};

        table.getColumns().add(colId);
        for(int i=0; i<cols.length; i++) {
            TableColumn<Student, String> col = new TableColumn<>(cols[i]);
            col.setCellValueFactory(new PropertyValueFactory<>(props[i]));
            table.getColumns().add(col);
        }

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                tfFirst.setText(newVal.getFirstName());
                tfLast.setText(newVal.getLastName());
                tfDept.setText(newVal.getDepartment());
                tfMajor.setText(newVal.getMajor());
                tfEmail.setText(newVal.getEmail());
                tfImageUrl.setText(newVal.getImageUrl());
                updateAvatarImage(newVal.getImageUrl());
            }
        });

        return table;
    }

    private VBox createLeftPanel() {
        avatarImageView = new ImageView(new Image(DEFAULT_AVATAR, 120, 120, true, true));
        avatarImageView.setFitWidth(120);
        avatarImageView.setFitHeight(120);
        avatarImageView.setPreserveRatio(true);

        StackPane imageCard = new StackPane(avatarImageView);
        imageCard.getStyleClass().add("avatar-container");
        imageCard.setPrefSize(130, 130);
        imageCard.setMaxSize(130, 130);

        VBox leftPanel = new VBox(imageCard);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setPadding(new Insets(20, 10, 10, 10));
        leftPanel.setPrefWidth(150);
        leftPanel.getStyleClass().add("left-panel");
        return leftPanel;
    }

    private VBox createRightPanel() {
        tfFirst.setPromptText("First Name"); tfLast.setPromptText("Last Name");
        tfDept.setPromptText("Department"); tfMajor.setPromptText("Major");
        tfEmail.setPromptText("Email"); tfImageUrl.setPromptText("imageURL");

        btnClear.setMaxWidth(Double.MAX_VALUE); btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnDelete.setMaxWidth(Double.MAX_VALUE); btnEdit.setMaxWidth(Double.MAX_VALUE);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox form = new VBox(10, tfFirst, tfLast, tfDept, tfMajor, tfEmail, tfImageUrl, spacer,
                new VBox(20, btnClear, btnAdd, btnDelete, btnEdit));
        form.setPadding(new Insets(15));
        form.setPrefWidth(220);
        form.getStyleClass().add("right-panel");

        setButtonActions();
        return form;
    }

    private VBox createBottomPanel() {
        Region greyBar = new Region(); greyBar.setPrefHeight(40);
        greyBar.setStyle("-fx-background-color: #9e9e9e;");
        Region brownBar = new Region(); brownBar.setPrefHeight(10);
        brownBar.setStyle("-fx-background-color: #c0812c;");
        return new VBox(greyBar, brownBar);
    }

    private void setButtonActions() {
        btnAdd.setOnAction(e -> {
            if (!validateFields()) return;
            data.add(new Student(idCounter++, tfFirst.getText(), tfLast.getText(),
                    tfDept.getText(), tfMajor.getText(), tfEmail.getText(), tfImageUrl.getText()));
            clearFields();
        });

        btnDelete.setOnAction(e -> {
            Student s = table.getSelectionModel().getSelectedItem();
            if (s != null) { data.remove(s); clearFields(); }
        });

        btnEdit.setOnAction(e -> {
            Student s = table.getSelectionModel().getSelectedItem();
            if (s == null || !validateFields()) return;
            s.setFirstName(tfFirst.getText()); s.setLastName(tfLast.getText());
            s.setDepartment(tfDept.getText()); s.setMajor(tfMajor.getText());
            s.setEmail(tfEmail.getText()); s.setImageUrl(tfImageUrl.getText());
            table.refresh();
            updateAvatarImage(s.getImageUrl());
        });

        btnClear.setOnAction(e -> { data.clear(); idCounter = 1; clearFields(); });
    }

    private void updateAvatarImage(String url) {
        try {
            if (url == null || url.trim().isEmpty()) throw new Exception();
            Image userImage = new Image(url, 120, 120, true, true, true);
            userImage.errorProperty().addListener((obs, old, err) -> {
                if (err) avatarImageView.setImage(new Image(DEFAULT_AVATAR, 120, 120, true, true));
            });
            avatarImageView.setImage(userImage);
        } catch (Exception e) {
            avatarImageView.setImage(new Image(DEFAULT_AVATAR, 120, 120, true, true));
        }
    }

    private boolean validateFields() {
        return !(tfFirst.getText().isEmpty() || tfLast.getText().isEmpty() ||
                tfDept.getText().isEmpty() || tfMajor.getText().isEmpty() ||
                tfEmail.getText().isEmpty() || tfImageUrl.getText().isEmpty());
    }

    private void clearFields() {
        tfFirst.clear(); tfLast.clear(); tfDept.clear();
        tfMajor.clear(); tfEmail.clear(); tfImageUrl.clear();
        updateAvatarImage(DEFAULT_AVATAR);
    }

    public static void main(String[] args) { launch(args); }
}
