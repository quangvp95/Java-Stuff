module demo {
    requires java.datatransfer;
    requires java.desktop;
    requires com.google.gson;
    opens demo.example to com.google.gson;
}