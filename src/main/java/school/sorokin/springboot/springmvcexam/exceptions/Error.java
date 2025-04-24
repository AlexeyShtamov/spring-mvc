package school.sorokin.springboot.springmvcexam.exceptions;

import java.time.LocalDateTime;

public class Error {
    private String shortMessage;
    private String mainMessage;
    private LocalDateTime exceptionDate;

    public Error(String shortMessage, String mainMessage, LocalDateTime exceptionDate) {
        this.shortMessage = shortMessage;
        this.mainMessage = mainMessage;
        this.exceptionDate = exceptionDate;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }

    public String getMainMessage() {
        return mainMessage;
    }

    public void setMainMessage(String mainMessage) {
        this.mainMessage = mainMessage;
    }

    public LocalDateTime getExceptionDate() {
        return exceptionDate;
    }

    public void setExceptionDate(LocalDateTime exceptionDate) {
        this.exceptionDate = exceptionDate;
    }
}
