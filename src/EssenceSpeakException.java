public class EssenceSpeakException extends Exception {
    private Essence essence;

    public EssenceSpeakException(Essence t) {
        super(t.getName() + " can't speak");
        essence = t;
    }

    public EssenceSpeakException(String message, Essence t) {
        super(message);
        essence = t;
    }

    public EssenceSpeakException(String message, Throwable cause, Essence t) {
        super(message, cause);
        essence = t;
    }

    public EssenceSpeakException(Throwable cause, Essence t) {
        super(cause);
        essence = t;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
