package Pages.StartPage;

public class StartPageModel {
    private String errorMessage = "";

    public void updateErrorMessage(String error) {
        errorMessage = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}