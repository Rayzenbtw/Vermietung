module gfs.gfs2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens gfs.gfs2 to javafx.fxml;
    exports gfs.gfs2;
}