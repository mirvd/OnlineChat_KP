package server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

class LoggerTest {
    @Mock
    Date date;
    SimpleDateFormat ft;
    FileOutputStream fos;

    @Test
    void createFileLog() {
    }
}